package com.juaracoding.service;

import com.juaracoding.core.IService;
import com.juaracoding.dto.rel.RelMenuDTO;
import com.juaracoding.dto.validation.ValAksesDTO;
import com.juaracoding.model.Akses;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.AksesRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AksesService implements IService<Akses> {

    @Autowired
    AksesRepo aksesRepo;

    @Override
    public ResponseEntity<Object> save(Akses akses, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Akses akses, HttpServletRequest request) {
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

    public Akses convertToEntity(ValAksesDTO valAksesDTO){
        Akses akses = new Akses();
        akses.setNama(valAksesDTO.getNama());
        akses.setDeskripsi(valAksesDTO.getDeskripsi());
        List<Menu> ltMenu = new ArrayList<Menu>();
        for (RelMenuDTO r:
             valAksesDTO.getLtMenu()) {
            Menu m = new Menu();
            m.setId(r.getId());
            ltMenu.add(m);
        }
        akses.setLtMenu(ltMenu);
        return akses;
    }
}
