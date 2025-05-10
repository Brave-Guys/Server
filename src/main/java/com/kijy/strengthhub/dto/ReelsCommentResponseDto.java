package com.kijy.strengthhub.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Builder
public class ReelsCommentResponseDto {
    private String rcommentId;
    private String reelsId;
    private Long writerId;
    private String nickname;
    private String profileImgUrl;
    private String parentId;
    private String content;
    private int likes;
    private LocalDateTime writeDate;
}
