package com.juaracoding.service;

import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.model.Menu;

import java.util.List;

/**
 *  Platform Code  - USM
 *  Modul Code - 01
 *  FV - FE 
 */
public class MenuService implements IService<Menu>, IReport<Menu> {

    @Override
    public void insert(Menu menu) {
        
    }

    @Override
    public void update(Long id, Menu menu) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Menu findBy(Long id) {
        return null;
    }

    @Override
    public List<Menu> findAll() {
        return List.of();
    }

    @Override
    public void uploadExcel(Menu menu) {
        
    }

    @Override
    public void downloadExcel(Menu menu) {

    }

    @Override
    public void uploadImage(Menu menu) {

    }

    @Override
    public void downloadImage(Menu menu) {

    }

    @Override
    public void downloadPdf(Menu menu) {

    }
}
