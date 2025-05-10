package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.RegisterRequestDto;
import com.kijy.strengthhub.dto.RegisterResponseDto;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDto register(RegisterRequestDto dto) {
        if (userRepository.existsByUserId(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        User user = User.builder()
                .userId(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getNickname())
                .email(dto.getEmail())
                .role("USER")
                .userPlanType("BEGINNER")
                .imgUrl(null)
                .build();

        User saved = userRepository.save(user);

        return RegisterResponseDto.builder()
                .id(saved.getId())
                .message("등록 완료")
                .build();
    }

    public void updateNickname(Long userId, String newNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setName(newNickname);
        userRepository.save(user);
    }

}
