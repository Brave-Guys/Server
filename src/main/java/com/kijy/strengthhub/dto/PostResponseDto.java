package com.kijy.strengthhub.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private Long writerId;
    private String name;
    private String content;
    private String category;
    private List<String> imageUrls;
    private String nickname;
    private int likeCount;
    private int commentCount;
}
