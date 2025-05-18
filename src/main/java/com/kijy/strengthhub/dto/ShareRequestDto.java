package com.kijy.strengthhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareRequestDto {
    private Long masterId;
    private Long userId;
    private String age;
    private String height;
    private String weight;
    private String gender;
    private String content;
}