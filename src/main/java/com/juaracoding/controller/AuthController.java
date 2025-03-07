package com.juaracoding.controller;


import com.juaracoding.dto.validation.ValLoginDTO;
import com.juaracoding.dto.validation.ValRegisDTO;
import com.juaracoding.dto.validation.ValVerifyOTPRegisDTO;
import com.juaracoding.service.AppUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AppUserDetailService appUserDetailService;

    // RBAC - Role Base Access Control
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody ValLoginDTO valLoginDTO,
                                        HttpServletRequest request){
        return appUserDetailService.Login(appUserDetailService.convertToEntity(valLoginDTO), request);
    }


    @PostMapping("/regis")
    public ResponseEntity<Object> regis(@Valid @RequestBody ValRegisDTO valRegisDTO,
                                        HttpServletRequest request){

        return appUserDetailService.regis(appUserDetailService.convertToEntity(valRegisDTO),request);
    }

    @PostMapping("/verify-regis")
    public ResponseEntity<Object> verifyRegis(@Valid @RequestBody ValVerifyOTPRegisDTO valVerifyOTPRegisDTO,
                                              HttpServletRequest request){

        return appUserDetailService.verifyRegis(appUserDetailService.convertToEntity(valVerifyOTPRegisDTO),request);
    }
}