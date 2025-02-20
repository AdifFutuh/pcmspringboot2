package com.juaracoding.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {


    // localhost:8080/hello - GET
    @GetMapping
    public String getData(HttpServletRequest req){
        String header = req.getHeader("syarat");
        if(header==null){
            return "Format Tidak Valid";
        }
        return "OK";
    }

    // localhost:8080/hello - POST
    @PostMapping
    public void postData(){
        System.out.println("Hello World - Post");
    }
}
