package com.kijy.strengthhub.dto;

import com.kijy.strengthhub.entity.Challenge;
import lombok.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeResponseDto {
    private Long id;
    private String name;
    private String description;
    private String videoUrl;
    private Long writerId;
    private String nickname;
    private Date createdAt;
    private Date endDate;

    public static ChallengeResponseDto from(Challenge challenge, String nickname) {
        return ChallengeResponseDto.builder()
                .id(challenge.getId())
                .name(challenge.getName())
                .description(challenge.getDescription())
                .videoUrl(challenge.getVideoUrl())
                .writerId(challenge.getWriterId())
                .nickname(nickname)
                .createdAt(challenge.getCreatedAt())
                .endDate(challenge.getEndDate())
                .build();
    }
}

