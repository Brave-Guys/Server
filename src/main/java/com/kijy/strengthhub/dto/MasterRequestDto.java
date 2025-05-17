package com.kijy.strengthhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MasterRequestDto {
    private String name;
    private String phone;
    private String career;
    private String intro;
    private String link;
    private List<String> parts;
    private List<String> certFileUrls;
    private List<String> portfolioUrls;
    private Long userId;
}
