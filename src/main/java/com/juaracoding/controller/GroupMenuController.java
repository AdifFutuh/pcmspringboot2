package com.juaracoding.controller;

import com.juaracoding.dto.validation.ValGroupMenuDTO;
import com.juaracoding.service.GroupMenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("group-menu")
public class GroupMenuController {


    @Autowired
    GroupMenuService groupMenuService;

    @GetMapping
    public ResponseEntity<Object> findAll(){
//        return groupMenuService.findAll();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body("/group-menu/v2");
    }

    @GetMapping("/v2")
    public ResponseEntity<Object> findAllV2(){
        return groupMenuService.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO){
        return groupMenuService.insert(groupMenuService.converToEntity(valGroupMenuDTO));
    }

}
