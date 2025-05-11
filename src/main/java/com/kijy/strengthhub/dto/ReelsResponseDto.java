package com.kijy.strengthhub.dto;

import com.kijy.strengthhub.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReelsResponseDto {

    private Long id;
    private String name;
    private String description;
    private String videoUrl;

    public static ReelsResponseDto from(Challenge challenge) {
        return new ReelsResponseDto(
                challenge.getId(),
                challenge.getName(),
                challenge.getDescription(),
                challenge.getVideoUrl()
        );
    }
}
