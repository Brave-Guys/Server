package com.kijy.strengthhub.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static String get(String key) {
        String fromEnv = System.getenv(key);
        if (fromEnv != null) return fromEnv;
        return dotenv.get(key);
    }
}
