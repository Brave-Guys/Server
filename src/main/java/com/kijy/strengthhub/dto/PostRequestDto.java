package com.kijy.strengthhub.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {
    private String writerId;
    private String name;
    private String content;
    private String category;
    private List<String> imageUrls;
}
