package com.juaracoding.controller;

import com.juaracoding.config.ContohConfig;
import com.juaracoding.httpclient.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    TestService testService;

    @PostMapping
    public ResponseEntity<Object> generateData(){
//        String x = testService.respData();
//        System.out.println(x);
//        int x = 1/0;
//        Rahasia321 ->
        // minimal 1 huruf kecil
        // minimal 1 huruf besar
        // minimal 1 angka
        // minimal 1 spesial karakter $-_@#
        // minimal 9 karakter maksimal 16
        // min 40 maks 255 , alfanumerik titik spasi <script>script jahat </script>
        Map<String,Object> m = new HashMap<>();
        m.put("message","URL Dipindahkan ke alamat baru");
        m.put("url","localhost:8080/test/v1");
        m.put("isiContoh", ContohConfig.getFlagSatu());

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body(m);
    }

    @GetMapping("/v1")
    public void generateDataNew(){
//        String x = testService.respData();
//        System.out.println(x);
        System.out.println("Berhasil!!");
    }

    @PutMapping
    public void generateData1(){
        String x = testService.respData1();
        System.out.println(x);
    }
}
