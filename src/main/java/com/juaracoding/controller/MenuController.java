package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValMenuDTO;
import com.juaracoding.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));
        return menuService.findAll(pageable,request);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValMenuDTO valMenuDTO, HttpServletRequest request){
        return menuService.save(menuService.converToEntity(valMenuDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValMenuDTO valMenuDTO, HttpServletRequest request){
        return menuService.update(id, menuService.converToEntity(valMenuDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id, HttpServletRequest request){
        return menuService.delete(id,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findById(
            @PathVariable(value = "id") Long id, HttpServletRequest request){
        return menuService.findById(id,request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findByParam(
            @PathVariable(value = "sort") String sort,
            @PathVariable(value = "sortBy") String sortBy,
            @PathVariable(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = sortColumnByMap(sortBy);
        switch (sort) {
            case "asc":pageable = PageRequest.of(page, size, Sort.by(sortBy));break;
            default: pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        return menuService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> uploadExcel(
            @RequestParam(value = "file") MultipartFile file, HttpServletRequest request){
        return menuService.uploadDataExcel(file,request);
    }

    private String sortColumnByMap(String sortBy){
        switch (sortBy){
            case "nama":sortBy = "nama";break;
            case "path":sortBy = "path";break;
            case "group":sortBy = "group";break;
            default:sortBy = "id";break;
        }
        return sortBy;
    }

    @GetMapping("/excel")
    public void downloadExcel(
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request,
            HttpServletResponse response){
        menuService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/pdf")
    public void downloadPdf(
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request,
            HttpServletResponse response){
        menuService.generateToPDF(column,value,request,response);
    }
}