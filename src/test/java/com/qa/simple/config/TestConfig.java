package com.qa.simple.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties не найден в resources");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения config.properties", e);
        }
    }

    public static String getBaseUrl() {
        String env = System.getProperty("selenide.baseUrl");
        if (env != null && !env.isBlank()) {
            return env;
        }
        String fileValue = props.getProperty("base.url");
        return (fileValue != null && !fileValue.isBlank()) ? fileValue : "https://example.com";
    }
}