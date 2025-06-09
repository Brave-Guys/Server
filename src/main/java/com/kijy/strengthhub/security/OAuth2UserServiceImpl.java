package com.kijy.strengthhub.security;

import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final OAuthService oAuthService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google, kakao 등
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = null;
        String name = null;
        String picture = null;

        if ("google".equals(provider)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            picture = (String) attributes.get("picture");

        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("nickname");
            picture = (String) profile.get("profile_image_url");
        }

        if (email == null) {
            // 이메일이 null인 경우, 임의로 유니크한 값 생성 (DB 중복 대비 시 적절한 처리 필요)
            email = "noemail-" + java.util.UUID.randomUUID().toString() + "@kakao.local";
        }

        // DB 처리 (회원가입 or 기존 유저 로그인)
        User user = oAuthService.processOAuthPostLogin(email, name, picture);

        // attributes Map 재구성
        Map<String, Object> customAttributes = new HashMap<>();
        customAttributes.put("email", email != null ? email : "");
        customAttributes.put("name", name != null ? name : "");
        customAttributes.put("picture", picture != null ? picture : "");

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                customAttributes,
                "email"
        );
    }

}
