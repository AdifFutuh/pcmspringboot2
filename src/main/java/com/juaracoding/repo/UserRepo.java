package com.juaracoding.repo;


import com.juaracoding.model.Menu;
import com.juaracoding.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    public Page<User> findByUsernameContainsIgnoreCase(Pageable pageable, String nama);
    public Page<User> findByAlamatContainsIgnoreCase(Pageable pageable, String nama);
    public Page<User> findByEmailContainsIgnoreCase(Pageable pageable, String nama);
    public Page<User> findByNoHpContainsIgnoreCase(Pageable pageable, String nama);

    @Query(value = "SELECT  x FROM User x WHERE CAST(DATEDIFF(year ,x.tanggalLahir,CURRENT_TIMESTAMP)AS STRING) LIKE CONCAT('%',?1,'%') ")
    public Page<User> cariUmur(Pageable pageable, String value);
}
