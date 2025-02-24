package com.juaracoding.model;

import java.util.List;
import java.util.Map;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/02/2025 19:37
@Last Modified 24/02/2025 19:37
Version 1.0
*/
public class ClassResponse {

    private String id;
    private Double total;
    private Map<String,Object> data;
    private List<Map<String,Object>> list;
    private List<ClassObj> list2;
    private ClassObj classObj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public List<ClassObj> getList2() {
        return list2;
    }

    public void setList2(List<ClassObj> list2) {
        this.list2 = list2;
    }

    public ClassObj getClassObj() {
        return classObj;
    }

    public void setClassObj(ClassObj classObj) {
        this.classObj = classObj;
    }
}
