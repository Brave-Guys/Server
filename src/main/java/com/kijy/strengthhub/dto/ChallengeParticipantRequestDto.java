package com.kijy.strengthhub.dto;

import lombok.Data;

@Data
public class ChallengeParticipantRequestDto {
    private Long challengeId;
    private Long writerId;
    private String content;
    private String videoUrl;
}
