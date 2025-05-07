package com.kijy.strengthhub.repository;

import com.kijy.strengthhub.entity.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findByUserIdAndDateBetweenOrderByDateAsc(String userId, Date start, Date end);
}
