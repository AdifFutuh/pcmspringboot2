package com.juaracoding.dto.response;

public class MenuLoginDTO {
    private String nama;
    private String path;
    private GroupMenuLoginDTO groupMenu;

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

    public GroupMenuLoginDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(GroupMenuLoginDTO groupMenu) {
        this.groupMenu = groupMenu;
    }
}