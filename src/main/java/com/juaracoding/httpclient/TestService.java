package com.juaracoding.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test-services", url = "localhost:8081/welcome")
public interface TestService {

    @GetMapping
    public String respData();

    @GetMapping("/to")
    public String respData1();
}
