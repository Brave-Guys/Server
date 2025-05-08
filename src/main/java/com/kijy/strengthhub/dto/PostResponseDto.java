package com.kijy.strengthhub.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long writerId;
    private String nickname;
    private String name;
    private String content;
    private String category;
    private List<String> imageUrls;
    private int likes;
    private int commentCount;
    private Date createDate;
    private Date updatedAt;
}
