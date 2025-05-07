package com.kijy.strengthhub.dto;

import com.kijy.strengthhub.entity.WorkoutLog;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutLogListResponseDto {
    private List<WorkoutLog> logs;
}
