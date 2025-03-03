package com.juaracoding.model;

/**
 *  Group Menu
 *  Menu
 *  Akses
 *  User
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Peserta",uniqueConstraints = @UniqueConstraint(
        columnNames = {"NamaPeserta","Alamat"},name = "unq-peserta-alamat"))
public class Peserta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPeserta")
    private Long idPeserta;//snake case -> camelCase -> PascalCase

    @Column(name = "NamaPeserta")
    @JsonProperty("nama-peserta")
    private String namaPeserta;

    /** adapt when migration */
    @Column(name = "Batch", length = 5,insertable = false,columnDefinition = "comment ")
    private String batch;

    @Column(name = "Alamat",length = 500)
    private String alamat;

    public Long getIdPeserta() {
        return idPeserta;
    }

    public void setIdPeserta(Long idPeserta) {
        this.idPeserta = idPeserta;
    }

    public String getNamaPeserta() {
        return namaPeserta;
    }

    public void setNamaPeserta(String namaPeserta) {
        this.namaPeserta = namaPeserta;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
