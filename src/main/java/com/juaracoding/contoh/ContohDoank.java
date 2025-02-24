package com.juaracoding.contoh;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ContohDoank {

    @Scheduled(fixedRate = 60000)
    public void executeData() throws InterruptedException {
        for(int i=1;i<=60;i++){
            Thread.sleep(1000);
            System.out.println("Print loop "+(i+1));
        }
        System.out.println("==========================");
    }
}
