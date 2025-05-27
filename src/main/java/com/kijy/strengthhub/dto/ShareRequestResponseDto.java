package com.kijy.strengthhub.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShareRequestResponseDto {
    private Long id;
    private Long masterId;
    private Long userId;
    private String nickname;
    private String profileImgUrl;
    private String age;
    private String height;
    private String weight;
    private String gender;
    private String content;
    private String status;
    private String parts;
}
