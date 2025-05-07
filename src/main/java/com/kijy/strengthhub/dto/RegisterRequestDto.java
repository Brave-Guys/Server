package com.kijy.strengthhub.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String email;
}
