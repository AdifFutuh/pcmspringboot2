package com.juaracoding.service;

import com.juaracoding.config.JwtConfig;
import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValLoginDTO;
import com.juaracoding.dto.validation.ValRegisDTO;
import com.juaracoding.dto.validation.ValVerifyOTPRegisDTO;
import com.juaracoding.model.User;
import com.juaracoding.repo.UserRepo;
import com.juaracoding.security.BcryptImpl;
import com.juaracoding.security.Crypto;
import com.juaracoding.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class AppUserDetailService  implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtility jwtUtility;

    private Random random = new Random();

    public ResponseEntity<Object> Login(User user , HttpServletRequest request){
        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        Map<String,Object> m = new HashMap<>();
        if(!optUser.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username / Password Salah - AUT00FV001");
        }
        User userNext = optUser.get();
        if(!userNext.getRegistered()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username / Password Salah - AUT00FV002");
        }

        if(!BcryptImpl.verifyHash((user.getUsername()+user.getPassword()),userNext.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username / Password Salah - AUT00FV003");
        }
        Map<String,Object> m1 = new HashMap<>();
        m1.put("id", userNext.getId());
        m1.put("phn", userNext.getNoHp());
        m1.put("ml", userNext.getEmail());
        /** khusus testing automation */
        String token = jwtUtility.doGenerateToken(m1,userNext.getUsername());
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
        m.put("token",token);
        m.put("menu",new ArrayList<>());
        return ResponseEntity.ok().body(m);
    }

    public ResponseEntity<Object> regis(User user , HttpServletRequest request){
        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        Map<String,Object> m = new HashMap<>();
        if(!optUser.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username / Password Salah - AUT00FV001");
        }
        User userNext = optUser.get();

        if(!userNext.getRegistered()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username / Password Salah - AUT00FV002");
        }

        if(!BcryptImpl.verifyHash((user.getUsername()+user.getPassword()),userNext.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username / Password Salah - AUT00FV003");
        }
        Map<String,Object> m1 = new HashMap<>();
        m1.put("id", user.getId());
        m1.put("phn", user.getNoHp());
        m1.put("ml", user.getEmail());
        /** khusus testing automation */
        String token = jwtUtility.doGenerateToken(m1,userNext.getUsername());
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
        m.put("token",token);
        m.put("menu",userNext.getAkses().getLtMenu());
        userRepo.save(user);
        return ResponseEntity.ok().body(m);
    }

    public ResponseEntity<Object> verifyRegis(User user , HttpServletRequest request){
        Optional<User> optUser = userRepo.findByEmail(user.getEmail());
        if(!optUser.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Tidak Valid !!");
        }
        User userNext = optUser.get();
        /** OTP nya sudah Valid */
        if(!BcryptImpl.verifyHash(user.getOtp(),userNext.getOtp())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP Tidak Valid, Cek Email Anda !!");
        }

        userNext.setRegistered(true);
        userNext.setUpdatedBy(String.valueOf(userNext.getId()));

        return ResponseEntity.status(HttpStatus.OK).body("Registrasi Berhasil");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User>  opUser = userRepo.findByUsername(username);
        if(!opUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),user.getAuthorities());
    }

    public User convertToEntity(ValRegisDTO valRegisDTO){
        return modelMapper.map(valRegisDTO, User.class);
    }

    public User convertToEntity(ValVerifyOTPRegisDTO valVerifyOTPRegisDTO){
        return modelMapper.map(valVerifyOTPRegisDTO, User.class);
    }

    public User convertToEntity(ValLoginDTO valLoginDTO){
        return modelMapper.map(valLoginDTO, User.class);
    }
}
