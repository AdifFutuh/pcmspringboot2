package com.juaracoding.controller;

import com.juaracoding.contoh.ContohDoank;
import com.juaracoding.model.ClassObj;
import com.juaracoding.model.ClassResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("res")
public class ContohRestController {
    // LUPA PASSWORD =>  TIDAK PERLU LOGIN
    // GANTI PASSWORD => SETELAH LOGIN
    @Autowired
    ContohDoank contohDoank;

    @Autowired
    Random random;

//    @Autowired
//    public ContohRestController(ContohDoank contohDoank, Random random) {
//        this.contohDoank = contohDoank;
//        this.random = random;
//    }

    @GetMapping
    public ClassResponse getData(){
        ClassResponse response = new ClassResponse();
        response.setId(UUID.randomUUID().toString());
        Map<String,Object> mapData = new HashMap<>();
        mapData.put("id-data",UUID.randomUUID().toString());
        mapData.put("gol-darah","AB");
        mapData.put("total-bayar",12000);
        List<Map<String,Object>> listData = new ArrayList<>();
        listData.add(mapData);
        mapData = new HashMap<>();
        mapData.put("id",UUID.randomUUID().toString());
        mapData.put("makanan","Cumi-Goreng-Mentega");
        mapData.put("bayar",13000L);
        mapData.put("kembalian",5000);
        response.setData(mapData);
        listData.add(mapData);
        response.setList(listData);
        ClassObj classObj = new ClassObj();
        classObj.setNama("Paul");
        classObj.setUmur(30);
        List<ClassObj> classObjList = new ArrayList<>();
        classObjList.add(classObj);
        classObj = new ClassObj();
        classObj.setNama("Willy");
        classObj.setUmur(20);
        classObjList.add(classObj);
        response.setClassObj(classObj);
        response.setList2(classObjList);
        response.setTotal(50000.0);
        return response;
    }
    @GetMapping("/a")
    public void callConfig(){
//        System.out.println(SMTPConfig.getEmailUserName());
//        System.out.println(SMTPConfig.getEmailPassword());
//        System.out.println(SMTPConfig.getEmailHost());
    }
}
