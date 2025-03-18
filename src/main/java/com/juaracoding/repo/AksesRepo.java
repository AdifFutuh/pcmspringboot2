package com.juaracoding.repo;

import com.juaracoding.dto.response.RespAksesDTO;
import com.juaracoding.model.Akses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AksesRepo extends JpaRepository<Akses, Long> {

    public Page<Akses> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public List<Akses> findByNamaContainsIgnoreCase(String nama);

    public Page<Akses> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);
    public List<Akses> findByDeskripsiContainsIgnoreCase(String nama);

    /** digunakan hanya untuk unit testing */

    public Optional<Akses> findTop1ByOrderByIdDesc();
}
