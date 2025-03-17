package com.juaracoding.dto.response;


import com.juaracoding.dto.rel.RelGroupMenuDTO;
import com.juaracoding.util.ConstantsMessage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RespMenuDTO {

    private Long id;
    private String nama;

    private String path;

    private RelGroupMenuDTO groupMenu;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RelGroupMenuDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(RelGroupMenuDTO groupMenu) {
        this.groupMenu = groupMenu;
    }
}
