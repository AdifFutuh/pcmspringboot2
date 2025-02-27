package com.juaracoding.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MstPeserta")
public class Peserta {

    @Id
    @Column(name = "IDPeserta")
    private Long idPeserta;

    @Column(name = "NamaPeserta", length = 40)
    private String nama;

    @Column(name = "Batch", length = 5)
    private String batch;

    @Column(name = "Alamat",length = 500)
    private String alamat;

    public Long getIdPeserta() {
        return idPeserta;
    }

    public void setIdPeserta(Long idPeserta) {
        this.idPeserta = idPeserta;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
