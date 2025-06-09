package com.kijy.strengthhub.service;

import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User processOAuthPostLogin(String email, String name, String pictureUrl) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        String randomUserId = email.split("@")[0] + "_" + UUID.randomUUID().toString().substring(0, 6);

        User user = User.builder()
                .userId(randomUserId)
                .email(email)
                .name(name)
                .password(passwordEncoder.encode("oauth2_default_password"))
                .role("USER")
                .userPlanType("BEGINNER")
                .imgUrl(pictureUrl)
                .build();

        return userRepository.save(user);
    }
}
