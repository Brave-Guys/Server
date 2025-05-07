package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);  // 아이디 중복 확인
    boolean existsByName(String name);      // 닉네임 중복 확인
}
