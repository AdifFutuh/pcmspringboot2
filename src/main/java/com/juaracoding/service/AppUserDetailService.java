package com.juaracoding.service;

import com.juaracoding.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@Transactional
public class AppUserDetailService  implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    private Random random = new Random();

    private ResponseEntity<Object> Login(){
        return null;
    }

    private ResponseEntity<Object> regis(){
        return null;
    }

    private ResponseEntity<Object> verifyRegis(){
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
