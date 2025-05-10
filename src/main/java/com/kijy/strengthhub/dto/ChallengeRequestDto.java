package com.kijy.strengthhub.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeRequestDto {
    private String name;
    private String description;
    private String videoUrl;
    private Long writerId;
    private Date endDate;
}
