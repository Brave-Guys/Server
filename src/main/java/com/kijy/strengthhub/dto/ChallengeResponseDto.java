package com.kijy.strengthhub.dto;

import lombok.*;
import java.util.Date;

@Getter @Builder
public class ChallengeResponseDto {
    private Long id;
    private String name;
    private String description;
    private String videoUrl;
    private Long writerId;
    private String nickname;
    private Date createdAt;
    private Date endDate;
}