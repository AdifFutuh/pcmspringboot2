package com.juaracoding.repo;


import com.juaracoding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String value);
    public Optional<User> findByUsername(String value);
}
