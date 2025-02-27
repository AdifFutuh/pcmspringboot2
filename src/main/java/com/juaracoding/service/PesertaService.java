package com.juaracoding.service;

import com.juaracoding.model.Peserta;
import com.juaracoding.repo.PesertaRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PesertaService {

    /**
     * 1. Insert Table Penjualan -> 1 Data
     * 2. Insert Table DetailPenjualan -> 5 Data
     * 3. Update Table Stock -> 5 Data
     */
    @Autowired
    private PesertaRepo pesertaRepo;

    public String save(Peserta peserta){
        pesertaRepo.save(peserta);
        return "success";
    }

    public List findall(String value){
        return pesertaRepo.findByNamaContainsIgnoreCase(value);
    }
}