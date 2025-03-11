package com.juaracoding.controller;


import com.juaracoding.dto.validation.ValLoginDTO;
import com.juaracoding.dto.validation.ValRegisDTO;
import com.juaracoding.dto.validation.ValVerifyOTPRegisDTO;
import com.juaracoding.service.AppUserDetailService;
import com.juaracoding.util.SendMailOTP;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/contoh-email")
    public String contohSendEmail(){
        SendMailOTP.verifyRegisOTP("Verifikasi OTP Registrasi",
                "Paul Christian Malau",
                "poll.chihuy@gmail.com",
                "121314"
                );
        return "OK";
    }
}