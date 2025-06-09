package com.kijy.strengthhub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class OAuthController {

    @GetMapping("/oauth2/success")
    public String success(@AuthenticationPrincipal OAuth2User user) {
        // 구글: email, name, picture
        // 카카오: kakao_account.profile.nickname, kakao_account.email 등
        String email = null;
        String name = null;
        String picture = null;

        if (user.getAttributes().containsKey("email")) {
            // 구글 유저
            email = user.getAttribute("email");
            name = user.getAttribute("name");
            picture = user.getAttribute("picture");
        } else if (user.getAttributes().containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) user.getAttribute("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("nickname");
            picture = (String) profile.get("profile_image_url");
        }

        log.info("✅ OAuth2 로그인 성공: name={}, email={}, picture={}", name, email, picture);

        return "로그인 성공!<br>이름: " + name + "<br>이메일: " + email;
    }

}
