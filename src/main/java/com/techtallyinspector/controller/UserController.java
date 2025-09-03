package com.techtallyinspector.controller;

import com.techtallyinspector.domain.User;
import com.techtallyinspector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // For development only
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public Flux<User> getAllActiveUsers() {
        return userRepository.findByActiveTrueOrderByLastName();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    // Don't return password in response
                    user.setPassword(null);
                    return ResponseEntity.ok(user);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user)
                .map(savedUser -> {
                    savedUser.setPassword(null); // Don't return password
                    return savedUser;
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    user.setId(id);
                    user.setCreatedAt(existingUser.getCreatedAt());
                    user.setUpdatedAt(LocalDateTime.now());
                    
                    // Only encode password if it's being changed
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                    } else {
                        user.setPassword(existingUser.getPassword());
                    }
                    
                    return userRepository.save(user);
                })
                .map(savedUser -> {
                    savedUser.setPassword(null); // Don't return password
                    return ResponseEntity.ok(savedUser);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    user.setActive(false);
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .then(Mono.just(ResponseEntity.ok().build()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/search")
    public Flux<User> searchUsers(@RequestParam String query) {
        return userRepository.findActiveUsersBySearch(query)
                .map(user -> {
                    user.setPassword(null); // Don't return passwords
                    return user;
                });
    }

    @GetMapping("/departments")
    public Flux<String> getDepartments() {
        return userRepository.findDistinctDepartments();
    }

    @GetMapping("/department/{department}")
    public Flux<User> getUsersByDepartment(@PathVariable String department) {
        return userRepository.findByDepartmentAndActiveTrue(department)
                .map(user -> {
                    user.setPassword(null); // Don't return passwords
                    return user;
                });
    }

    @GetMapping("/count")
    public Mono<Long> getTotalActiveUsersCount() {
        return userRepository.countActiveUsers();
    }

    @GetMapping("/check-login/{login}")
    public Mono<Boolean> checkLoginExists(@PathVariable String login) {
        return userRepository.existsByLogin(login);
    }

    @GetMapping("/check-email/{email}")
    public Mono<Boolean> checkEmailExists(@PathVariable String email) {
        return userRepository.existsByEmail(email);
    }
}