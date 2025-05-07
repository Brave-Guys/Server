package com.kijy.strengthhub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {
    private Long id;
    private String message;
}
