package com.juaracoding.repo;


import com.juaracoding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends JpaRepository<User, Long> {
}
