package com.kijy.strengthhub.dto;

import lombok.Data;

@Data
public class WorkoutLogRequestDto {
    private String userId;
    private String name;
    private String date; // 문자열로 받고 변환
    private Integer duration;
    private Integer distance;
    private Integer sets;
    private Integer reps;
    private Integer weight;
    private String exerciseType;
    private String part;
}
