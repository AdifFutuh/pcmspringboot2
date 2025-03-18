package com.juaracoding.dto.validation;


import com.juaracoding.dto.rel.RelGroupMenuDTO;
import com.juaracoding.dto.report.RepGroupMenuDTO;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.util.ConstantsMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.tomcat.util.bcel.Const;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class ValMenuDTO {

    @Pattern(regexp = "^[\\w\\s]{5,20}$",message = ConstantsMessage.VAL_MENU_NAMA)
    private String nama;

    @Pattern(regexp = "^[\\w\\/-]{5,20}$",message = ConstantsMessage.VAL_MENU_PATH)
    private String path;

    @NotNull(message = ConstantsMessage.VAL_MENU_GROUP)
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
