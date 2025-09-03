package com.techtallyinspector.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.techtallyinspector.repository.EquipmentRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Scheduled job to check for equipment with expiring warranties
 */
@Component
public class WarrantyCheckJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(WarrantyCheckJob.class);

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Starting warranty check job at {}", LocalDateTime.now());

        try {
            // Check for equipment with warranty expiring in the next 30 days
            equipmentRepository.findWarrantyExpiringSoon()
                .doOnNext(equipment -> {
                    logger.info("Equipment '{}' (Serial: {}) has warranty expiring on {}",
                        equipment.getName(), 
                        equipment.getSerialNumber(), 
                        equipment.getWarrantyEndDate());
                    
                    // Here you could send notifications, create tasks, etc.
                    // For now, we just log the information
                })
                .count()
                .doOnSuccess(count -> {
                    if (count > 0) {
                        logger.warn("Found {} equipment items with expiring warranty", count);
                    } else {
                        logger.info("No equipment found with expiring warranty");
                    }
                })
                .doOnError(error -> {
                    logger.error("Error during warranty check job execution", error);
                })
                .block(); // Block to wait for completion in scheduler context

        } catch (Exception e) {
            logger.error("Warranty check job failed", e);
            throw new JobExecutionException(e);
        }

        logger.info("Completed warranty check job at {}", LocalDateTime.now());
    }
}