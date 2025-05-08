package com.kijy.strengthhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long writerId;
    private String content;
    private Long parentId;
}
