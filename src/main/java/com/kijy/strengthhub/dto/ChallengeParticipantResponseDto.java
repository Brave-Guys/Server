package com.kijy.strengthhub.dto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeParticipantResponseDto {
    private Long id;
    private Long challengeId;
    private Long writerId;
    private String nickname;
    private String content;
    private String videoUrl;
    private Date writeDate;
    private String profileImgUrl;
}
