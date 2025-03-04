package com.juaracoding.dto.validation;


import com.juaracoding.dto.report.RepGroupMenuDTO;
import com.juaracoding.model.GroupMenu;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class ValMenuDTO {


    private String nama;

    private String path;

    private RepGroupMenuDTO groupMenu;

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

    public RepGroupMenuDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(RepGroupMenuDTO groupMenu) {
        this.groupMenu = groupMenu;
    }
}
