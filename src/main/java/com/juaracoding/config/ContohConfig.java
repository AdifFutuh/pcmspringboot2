package com.juaracoding.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Random;

@Configuration
@PropertySource("classpath:contoh.properties")
public class ContohConfig {

    private static String flagSatu;

    public static String getFlagSatu() {
        return flagSatu;
    }

    @Value("${flag.satu}")
    private void setFlagSatu(String flagSatu) {
        ContohConfig.flagSatu = flagSatu;
    }

    @Bean
    public Random getRandom() {
        return new Random();
    }
}
