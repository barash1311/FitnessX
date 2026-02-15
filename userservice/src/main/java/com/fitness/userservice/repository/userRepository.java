package com.fitness.userservice.repository;

import com.fitness.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface userRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
