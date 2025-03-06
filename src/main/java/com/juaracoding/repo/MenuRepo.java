package com.juaracoding.repo;

import com.juaracoding.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepo extends JpaRepository<Menu,Long> {
}
