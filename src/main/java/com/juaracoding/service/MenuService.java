package com.juaracoding.service;

import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.MenuRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 *  Platform Code  - USM
 *  Modul Code - 01
 *  FV - FE 
 */
@Service
@Transactional
public class MenuService implements IService<Menu> {

    @Autowired
    private MenuRepo menuRepo;


    @Override
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        return null;
    }
}
