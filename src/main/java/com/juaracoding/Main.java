package com.juaracoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableScheduling
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

//        String s = "public static void main(String[] args) {\n" +
//                "        SpringApplication.run(Main.class, args);\n" +
//                "    }";
        Object obj = new Object();
//        obj.exec(s);
    }
}