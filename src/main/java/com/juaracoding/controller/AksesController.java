package com.juaracoding.controller;


import com.juaracoding.dto.validation.ValAksesDTO;
import com.juaracoding.service.AksesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("akses")
public class AksesController {

    @Autowired
    private AksesService aksesService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValAksesDTO valAksesDTO, HttpServletRequest request) {
        return aksesService.save(aksesService.convertToEntity(valAksesDTO),request);
    }
}
