package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByName(String name);
    List<User> findByRole(String role);
    Optional<User> findByEmail(String email);
}
