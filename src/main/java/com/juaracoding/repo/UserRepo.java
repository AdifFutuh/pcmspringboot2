package com.juaracoding.repo;


import com.juaracoding.model.Menu;
import com.juaracoding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    /** Select * From MstUser WHERE Email = ? AND IsRegistered = ? */
    public Optional<User> findByEmailAndIsRegistered(String value, Boolean isRegistered);
    public Optional<User> findByEmail(String value);
    public Optional<User> findByNoHpAndIsRegistered(String value, Boolean isRegistered);

    /** Select * From MstUser WHERE Username = ? */
    public Optional<User> findByUsername(String value);

    /** digunakan hanya untuk unit testing */
    public Optional<Menu> findTopByOrderByIdDesc();
}
