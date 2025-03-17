package com.juaracoding.coretan;

import com.juaracoding.dto.response.RespGroupMenuDTO;
import com.juaracoding.dto.response.RespProductDTO;
import com.juaracoding.util.GlobalFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 17/03/2025 20:02
@Last Modified 17/03/2025 20:02
Version 1.0
*/
public class RefleksiJava {

    public static void main(String[] args) {

        List<RespGroupMenuDTO> l = new ArrayList<>();
        RespGroupMenuDTO r = new RespGroupMenuDTO();
        r.setId(1L);
        r.setNama("AA");
        r.setDeskripsi("AA11");
        l.add(r);

        r = new RespGroupMenuDTO();
        r.setId(2L);
        r.setNama("BB");
        r.setDeskripsi("BB22");
        l.add(r);

//        List<RespProductDTO> l = new ArrayList<>();
//        RespProductDTO r = new RespProductDTO();
////        r.setId(1L);
//        r.setNama("AA");
//        r.setDesc("AA11");
//        l.add(r);
//
//        r = new RespProductDTO();
////        r.setId(2L);
//        r.setNama("BB");
//        r.setDesc("BB22");
//        l.add(r);

        List<Map<String,Object>> lMap = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            lMap.add(GlobalFunction.convertClassToMap(l.get(i)));
        }

        Map<String,Object> m = GlobalFunction.convertClassToMap(new RespGroupMenuDTO());
        List<String> listHelper = new ArrayList<>();
        for (Map.Entry<String,Object> f : m.entrySet()) {
            System.out.println("key:"+f.getKey()+" value:"+f.getValue());
            listHelper.add(f.getKey());
        }

        for (int i = 0; i < lMap.size(); i++) {
            Map<String,Object> mm = lMap.get(i);
            for (int j = 0; j < listHelper.size(); j++) {
                System.out.println(mm.get(listHelper.get(j)));
            }
            System.out.println("===================");
        }
    }
}
