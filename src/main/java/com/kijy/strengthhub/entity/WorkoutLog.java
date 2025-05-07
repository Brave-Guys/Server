package com.kijy.strengthhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "workout_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Integer duration;
    private Integer distance;
    private Integer sets;
    private Integer reps;
    private Integer weight;

    private String exerciseType;
    private String part;
}
