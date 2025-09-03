package com.techtallyinspector.repository;

import com.techtallyinspector.domain.Equipment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EquipmentRepository extends ReactiveCrudRepository<Equipment, Long> {

    Flux<Equipment> findByStatus(Equipment.EquipmentStatus status);

    Flux<Equipment> findByGroupId(Long groupId);

    Flux<Equipment> findByResponsibleUserId(Long userId);

    Flux<Equipment> findByDeviceTypeId(Long deviceTypeId);

    Mono<Equipment> findBySerialNumber(String serialNumber);

    Mono<Equipment> findByInventoryNumber(String inventoryNumber);

    @Query("SELECT * FROM equipment WHERE " +
           "(:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:status IS NULL OR status = :status) AND " +
           "(:groupId IS NULL OR group_id = :groupId) AND " +
           "(:responsibleUserId IS NULL OR responsible_user_id = :responsibleUserId) AND " +
           "(:deviceTypeId IS NULL OR device_type_id = :deviceTypeId) " +
           "ORDER BY name")
    Flux<Equipment> findByFilter(String name, String status, Long groupId, 
                                Long responsibleUserId, Long deviceTypeId);

    @Query("SELECT COUNT(*) FROM equipment WHERE " +
           "(:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:status IS NULL OR status = :status) AND " +
           "(:groupId IS NULL OR group_id = :groupId) AND " +
           "(:responsibleUserId IS NULL OR responsible_user_id = :responsibleUserId) AND " +
           "(:deviceTypeId IS NULL OR device_type_id = :deviceTypeId)")
    Mono<Long> countByFilter(String name, String status, Long groupId, 
                            Long responsibleUserId, Long deviceTypeId);

    @Query("SELECT * FROM equipment WHERE " +
           "(LOWER(name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(serial_number) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(inventory_number) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(location) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY name")
    Flux<Equipment> searchEquipment(String search);

    @Query("SELECT COUNT(*) FROM equipment WHERE status = :status")
    Mono<Long> countByStatus(String status);

    @Query("SELECT * FROM equipment WHERE warranty_end_date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days'")
    Flux<Equipment> findWarrantyExpiringSoon();

    @Query("SELECT DISTINCT location FROM equipment WHERE location IS NOT NULL ORDER BY location")
    Flux<String> findDistinctLocations();

    Mono<Boolean> existsBySerialNumber(String serialNumber);

    Mono<Boolean> existsByInventoryNumber(String inventoryNumber);
}