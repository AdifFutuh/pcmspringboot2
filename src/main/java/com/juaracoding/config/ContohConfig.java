package com.juaracoding.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
}
