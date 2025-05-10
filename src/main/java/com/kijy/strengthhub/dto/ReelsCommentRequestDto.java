package com.kijy.strengthhub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReelsCommentRequestDto {
    private String reelsId;
    private Long writerId;
    private String content;
    private Long parentId; // 변경
}
