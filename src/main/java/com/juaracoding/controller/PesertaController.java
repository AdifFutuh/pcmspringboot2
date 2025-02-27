package com.juaracoding.controller;


import com.juaracoding.model.Peserta;
import com.juaracoding.service.PesertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("peserta")
public class PesertaController {

    @Autowired
    private PesertaService pesertaService;

    @PostMapping
    public String save(@RequestBody Peserta p){
        return pesertaService.save(p);
    }

    @GetMapping
    public List findall(@RequestParam(value = "val") String value){
        return pesertaService.findall(value);
    }
}
