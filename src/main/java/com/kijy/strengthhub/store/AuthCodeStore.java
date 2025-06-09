package com.kijy.strengthhub.store;

import com.kijy.strengthhub.model.EmailAuth;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthCodeStore {

    private final Map<String, EmailAuth> store = new ConcurrentHashMap<>();
    private static final Duration TTL = Duration.ofMinutes(5);

    public void save(String email, String code) {
        store.put(email, new EmailAuth(code, LocalDateTime.now().plus(TTL)));
    }

    public boolean verify(String email, String code) {
        EmailAuth auth = store.get(email);
        return auth != null && !auth.isExpired() && auth.getCode().equals(code);
    }

    public void remove(String email) {
        store.remove(email);
    }
}
