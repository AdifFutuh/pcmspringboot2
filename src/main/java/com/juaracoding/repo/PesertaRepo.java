package com.juaracoding.repo;

import com.juaracoding.model.Peserta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PesertaRepo extends JpaRepository<Peserta, Long> {

    List<Peserta> findByNamaContainsIgnoreCase(String nama);
}