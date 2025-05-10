package com.kijy.strengthhub.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReelsCommentResponseDto {
    private Long rcommentId;
    private String reelsId;
    private Long writerId;
    private String nickname;
    private String profileImgUrl;
    private String content;
    private Long parentId;
    private LocalDateTime writeDate;
}
