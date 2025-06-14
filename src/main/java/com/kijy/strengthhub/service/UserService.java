package com.kijy.strengthhub.service;

import com.kijy.strengthhub.dto.RegisterRequestDto;
import com.kijy.strengthhub.dto.RegisterResponseDto;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 이메일을 찾을 수 없습니다."));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
    }

    public void updateNickname(Long userId, String newNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setName(newNickname);
        userRepository.save(user);
    }

    @Transactional
    public void updateProfileImage(Long id, String imgUrl) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        user.setImgUrl(imgUrl);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void updatePasswordByEmail(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 등록된 사용자가 없습니다."));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<User> getSeniorUsers() {
        return userRepository.findByRole("SENIOR");
    }

    @Transactional
    public void updateUserPlanType(Long id, String userPlanType) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.setUserPlanType(userPlanType.toUpperCase());
        userRepository.save(user);
    }


}
