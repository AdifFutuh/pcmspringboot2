package com.juaracoding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtConfig {

    private static String secretKey;
    private static Long timeExpiration;

    public static String getSecretKey() {
        return secretKey;
    }

    @Value("${secret.key}")
    private void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public static Long getTimeExpiration() {
        return timeExpiration;
    }

    @Value("${time.expiration}")
    private void setTimeExpiration(Long timeExpiration) {
        JwtConfig.timeExpiration = timeExpiration;
    }
}