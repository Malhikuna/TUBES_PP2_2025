package org.example.model;

public class AnggotaModel {
    private String idAnggota, nama, noTelp, statusAktif;

    public AnggotaModel(String idAnggota, String nama, String noTelp, String statusAktif) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.noTelp = noTelp;
        this.statusAktif = statusAktif;
    }

    public String getIdAnggota() { return idAnggota; }
    public String getNama() { return nama; }
    public String getNoTelp() { return noTelp; }
    public String getStatusAktif() { return statusAktif; }
}