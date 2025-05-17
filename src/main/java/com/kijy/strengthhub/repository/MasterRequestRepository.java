package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.MasterRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MasterRequestRepository extends JpaRepository<MasterRequest, Long> {
    List<MasterRequest> findByStatus(String status);
}
