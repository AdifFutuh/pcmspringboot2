package com.juaracoding.dto.validation;


import com.juaracoding.dto.rel.RelGroupMenuDTO;
import com.juaracoding.dto.report.RepGroupMenuDTO;
import com.juaracoding.model.GroupMenu;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class ValMenuDTO {

    @Pattern(regexp = "^[\\w\\s]{5,50}$",message = "Alfanumerik dengan spasi min 5 maks 50 karakter")
    private String nama;

    @Pattern(regexp = "^[\\w\\s\\/]{5,50}$",message = "Alfanumerik dengan spasi min 5 maks 50 karakter")
    private String path;

    private RelGroupMenuDTO groupMenu;

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
