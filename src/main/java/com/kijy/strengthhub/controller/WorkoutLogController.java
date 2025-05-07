package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.WorkoutLogListResponseDto;
import com.kijy.strengthhub.dto.WorkoutLogRequestDto;
import com.kijy.strengthhub.entity.WorkoutLog;
import com.kijy.strengthhub.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workoutlogs")
@RequiredArgsConstructor
public class WorkoutLogController {

    private final WorkoutLogService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody WorkoutLogRequestDto dto) throws ParseException {
        WorkoutLog saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 특정 날짜 운동 기록 조회
    @GetMapping
    public ResponseEntity<List<WorkoutLog>> getLogsByDate(
            @RequestParam String userId,
            @RequestParam String date
    ) throws ParseException {
        List<WorkoutLog> logs = service.getLogsByDate(userId, date);
        return ResponseEntity.ok(logs); // 그냥 배열 리턴
    }

    // 날짜 범위 운동 기록 조회
    @GetMapping("/range")
    public ResponseEntity<Map<String, List<WorkoutLog>>> getLogsInRange(
            @RequestParam String userId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) throws ParseException {
        List<WorkoutLog> logs = service.getLogsInRange(userId, startDate, endDate);
        return ResponseEntity.ok(Map.of("logs", logs)); // logs 키 포함한 JSON
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "운동 기록 삭제 완료"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody WorkoutLogRequestDto dto) throws ParseException {
        WorkoutLog updated = service.update(id, dto);
        return ResponseEntity.ok(Map.of("message", "운동 기록 수정 완료", "log", updated));
    }
}
