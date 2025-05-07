package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.WorkoutLogRequestDto;
import com.kijy.strengthhub.entity.WorkoutLog;
import com.kijy.strengthhub.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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

    @GetMapping
    public ResponseEntity<?> getByDate(@RequestParam String userId, @RequestParam String date) throws ParseException {
        return ResponseEntity.ok(service.getLogsByDate(userId, date));
    }

    @GetMapping("/range")
    public ResponseEntity<?> getRange(@RequestParam String userId,
                                      @RequestParam String startDate,
                                      @RequestParam String endDate) throws ParseException {
        return ResponseEntity.ok(Map.of("logs", service.getLogsInRange(userId, startDate, endDate)));
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
