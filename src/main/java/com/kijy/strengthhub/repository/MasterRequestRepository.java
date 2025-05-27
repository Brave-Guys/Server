package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.MasterRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MasterRequestRepository extends JpaRepository<MasterRequest, Long> {
    List<MasterRequest> findByStatus(String status);
    Optional<MasterRequest> findByUserId(Long userId);
}
