package com.juaracoding.controller;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("welcom")
public class ContohSSRControler {

    @GetMapping
    public String data(Model model){
        return "ok";
    }
}
