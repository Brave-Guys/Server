package com.kijy.strengthhub.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ShareCommentResponseDto {
    private Long id;
    private Long writerId;
    private String nickname;
    private String profileImgUrl;
    private LocalDate date;
    private String content;
    private String picture;
    private LocalDateTime createdAt;
}
