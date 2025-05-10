package com.kijy.strengthhub.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReelsCommentRequestDto {
    private String reelsId;
    private Long writerId;
    private String parentId; // 답글이 아니라면 null
    private String content;
}
