package com.techtallyinspector.controller;

import com.techtallyinspector.domain.Equipment;
import com.techtallyinspector.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*") // For development only
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping
    public Flux<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Equipment>> getEquipmentById(@PathVariable Long id) {
        return equipmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Equipment> createEquipment(@RequestBody Equipment equipment) {
        equipment.setCreatedAt(LocalDateTime.now());
        equipment.setUpdatedAt(LocalDateTime.now());
        return equipmentRepository.save(equipment);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Equipment>> updateEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
        return equipmentRepository.findById(id)
                .flatMap(existingEquipment -> {
                    equipment.setId(id);
                    equipment.setCreatedAt(existingEquipment.getCreatedAt());
                    equipment.setUpdatedAt(LocalDateTime.now());
                    return equipmentRepository.save(equipment);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteEquipment(@PathVariable Long id) {
        return equipmentRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @GetMapping("/status/{status}")
    public Flux<Equipment> getEquipmentByStatus(@PathVariable String status) {
        try {
            Equipment.EquipmentStatus equipmentStatus = Equipment.EquipmentStatus.fromValue(status);
            return equipmentRepository.findByStatus(equipmentStatus);
        } catch (IllegalArgumentException e) {
            return Flux.empty();
        }
    }

    @GetMapping("/user/{userId}")
    public Flux<Equipment> getEquipmentByResponsibleUser(@PathVariable Long userId) {
        return equipmentRepository.findByResponsibleUserId(userId);
    }

    @GetMapping("/warranty-expiring")
    public Flux<Equipment> getWarrantyExpiringSoon() {
        return equipmentRepository.findWarrantyExpiringSoon();
    }

    @GetMapping("/search")
    public Flux<Equipment> searchEquipment(@RequestParam String query) {
        return equipmentRepository.searchEquipment(query);
    }

    @GetMapping("/count")
    public Mono<Long> getTotalCount() {
        return equipmentRepository.count();
    }

    @GetMapping("/count/status/{status}")
    public Mono<Long> getCountByStatus(@PathVariable String status) {
        return equipmentRepository.countByStatus(status);
    }
}