package com.techtallyinspector.repository;

import com.techtallyinspector.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByLogin(String login);

    Mono<User> findByEmail(String email);

    Flux<User> findByActiveTrue();

    Flux<User> findByActiveTrueOrderByLastName();

    @Query("SELECT * FROM users WHERE active = true AND " +
           "(LOWER(login) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(firstname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(lastname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(email) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY lastname, firstname")
    Flux<User> findActiveUsersBySearch(String search);

    @Query("SELECT COUNT(*) FROM users WHERE active = true")
    Mono<Long> countActiveUsers();

    @Query("SELECT * FROM users WHERE department = :department AND active = true")
    Flux<User> findByDepartmentAndActiveTrue(String department);

    @Query("SELECT DISTINCT department FROM users WHERE department IS NOT NULL AND active = true ORDER BY department")
    Flux<String> findDistinctDepartments();

    Mono<Boolean> existsByLogin(String login);

    Mono<Boolean> existsByEmail(String email);
}