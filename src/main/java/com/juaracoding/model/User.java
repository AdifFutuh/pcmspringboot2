package com.juaracoding.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Table(name = "MstUser")
public class User implements UserDetails {

    @Id
    @Column(name = "IDUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",length = 40,nullable = false,unique = true)
    private String username;

    @Column(name = "Password",length = 60,nullable = false,unique = true)
    private String password;
    @Column(name = "Email",length = 64,nullable = false,unique = true)
    private String email;
    @Column(name = "Alamat",length = 255,nullable = false)
    private String alamat;
    @Column(name = "NoHp",length = 16,nullable = false,unique = true)
    private String noHp;
    @Column(name = "Nama" , length = 50, nullable = false)
    private String nama;

    @Column(name = "TanggalLahir")
    private LocalDate tanggalLahir;

    @Transient
    private Integer umur;

    @Column(name = "ProfilePicture")
    private String pathImage;

    @Column(name = "LinkProfilePicture")
    private String linkImage;

    /** ubah saat migrasi DB */
    @Column(name = "IsRegistered",columnDefinition = ("bit default 0"))
    private Boolean isRegistered=false;

    @ManyToOne
    @JoinColumn(name = "IDAkses",foreignKey = @ForeignKey(name = "fk-user-to-akses"))
    private Akses akses;

    @Column(name = "OTP",length = 60)
    private String otp;

    @Column(name = "CreatedBy",updatable = false,nullable = false)
    private String createdBy;
    @Column(name = "CreatedDate",updatable = false,nullable = false)
    private Date createdDate = new Date();

    @Column(name = "UpdatedBy",insertable = false)
    private String updatedBy;
    @Column(name = "UpdatedDate",insertable = false)
    private Date updatedDate;

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Menu> lt = this.akses.getLtMenu();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Menu menu :
                lt) {
            grantedAuthorities.add(new SimpleGrantedAuthority(menu.getNama()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public Boolean getRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Integer getUmur() {
        return Period.between(tanggalLahir,LocalDate.now()).getYears();
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public Akses getAkses() {
        return akses;
    }

    public void setAkses(Akses akses) {
        this.akses = akses;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }


}