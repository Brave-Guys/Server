package com.kijy.strengthhub.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String writerId;
    private String name;
    private String content;
    private String category;
    private String imageUrls;
    private String nickname;
    private int likeCount;
    private int commentCount;
}
