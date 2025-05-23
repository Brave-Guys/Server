package com.kijy.strengthhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String userId;
    private String nickname;
    private String email;
    private String role;
    private String userPlanType;
    private String profileImgUrl;
}
