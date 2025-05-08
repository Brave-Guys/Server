package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.WorkoutLogRequestDto;
import com.kijy.strengthhub.entity.WorkoutLog;
import com.kijy.strengthhub.repository.WorkoutLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;

    public WorkoutLog save(WorkoutLogRequestDto dto) {
        LocalDate localDate = LocalDate.parse(dto.getDate());
        ZonedDateTime zdt = localDate.atTime(9, 0).atZone(ZoneId.of("Asia/Seoul"));
        Date parsedDate = Date.from(zdt.toInstant());

        WorkoutLog log = WorkoutLog.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .date(parsedDate)
                .duration(dto.getDuration())
                .distance(dto.getDistance())
                .sets(dto.getSets())
                .reps(dto.getReps())
                .weight(dto.getWeight())
                .exerciseType(dto.getExerciseType())
                .part(dto.getPart())
                .build();

        return workoutLogRepository.save(log);
    }


    // 특정 날짜의 운동 기록
    public List<WorkoutLog> getLogsByDate(String userId, String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr);
        ZonedDateTime startZdt = localDate.atStartOfDay(ZoneId.of("Asia/Seoul"));
        ZonedDateTime endZdt = startZdt.plusDays(1); // 다음날 00:00

        Date start = Date.from(startZdt.toInstant());
        Date end = Date.from(endZdt.toInstant());

        return workoutLogRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, start, end);
    }

    // 날짜 범위의 운동 기록
    public List<WorkoutLog> getLogsInRange(String userId, String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr).plusDays(1); // 종료일 포함하려면 하루 더해줌

        ZonedDateTime startZdt = startDate.atStartOfDay(ZoneId.of("Asia/Seoul"));
        ZonedDateTime endZdt = endDate.atStartOfDay(ZoneId.of("Asia/Seoul"));

        Date start = Date.from(startZdt.toInstant());
        Date end = Date.from(endZdt.toInstant());

        return workoutLogRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, start, end);
    }

    public void delete(Long id) {
        workoutLogRepository.deleteById(id);
    }

    public WorkoutLog update(Long id, WorkoutLogRequestDto dto) {
        WorkoutLog log = workoutLogRepository.findById(id).orElseThrow();

        LocalDate localDate = LocalDate.parse(dto.getDate());
        ZonedDateTime zdt = localDate.atTime(9, 0).atZone(ZoneId.of("Asia/Seoul"));
        Date parsedDate = Date.from(zdt.toInstant());

        log.setName(dto.getName());
        log.setDate(parsedDate);
        log.setDuration(dto.getDuration());
        log.setDistance(dto.getDistance());
        log.setSets(dto.getSets());
        log.setReps(dto.getReps());
        log.setWeight(dto.getWeight());
        log.setExerciseType(dto.getExerciseType());
        log.setPart(dto.getPart());

        return workoutLogRepository.save(log);
    }

}
