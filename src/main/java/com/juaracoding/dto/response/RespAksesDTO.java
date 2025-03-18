package com.juaracoding.dto.response;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juaracoding.dto.rel.RelMenuDTO;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.List;

public class RespAksesDTO  {


    private Long id;

    private String nama;

    private String deskripsi;

    private List<RelMenuDTO> ltMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public List<RelMenuDTO> getLtMenu() {
        return ltMenu;
    }

    public void setLtMenu(List<RelMenuDTO> ltMenu) {
        this.ltMenu = ltMenu;
    }
}
