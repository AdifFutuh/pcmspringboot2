package com.juaracoding.core;

public interface IReport<T>{

    public void uploadExcel(T t);//051 060
    public void downloadExcel(T t);//061 070
    public void uploadImage(T t);
    public void downloadImage(T t);
    public void downloadPdf(T t);
}