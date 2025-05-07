package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.WorkoutLogRequestDto;
import com.kijy.strengthhub.entity.WorkoutLog;
import com.kijy.strengthhub.repository.WorkoutLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;

    public WorkoutLog save(WorkoutLogRequestDto dto) throws ParseException {
        WorkoutLog log = WorkoutLog.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .date(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDate()))
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

    public List<WorkoutLog> getLogsByDate(String userId, String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.DATE, 1);
        Date end = c.getTime();
        return workoutLogRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, start, end);
    }

    public List<WorkoutLog> getLogsInRange(String userId, String startDate, String endDate) throws ParseException {
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        return workoutLogRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, start, end);
    }

    public void delete(Long id) {
        workoutLogRepository.deleteById(id);
    }

    public WorkoutLog update(Long id, WorkoutLogRequestDto dto) throws ParseException {
        WorkoutLog log = workoutLogRepository.findById(id).orElseThrow();
        log.setName(dto.getName());
        log.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDate()));
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
