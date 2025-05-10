package com.kijy.strengthhub.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long writerId;
    private String nickname;
    private String content;
    private Long parentId;
    private int likes;
    private Date writeDate;
    private Date updatedAt;
    private String profileImgUrl;
}
