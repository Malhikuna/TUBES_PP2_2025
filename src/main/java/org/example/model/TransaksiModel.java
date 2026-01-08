package org.example.model;

import java.util.Date;

public class TransaksiModel {
    private int idLog;
    private String idAnggota;
    private String idBuku;
    private Date tglPinjam;
    private Date tglKembali;
    private String status;
    private double denda;

    public TransaksiModel(int idLog, String idAnggota, String idBuku, Date tglPinjam, Date tglKembali, String status, double denda) {
        this.idLog = idLog;
        this.idAnggota = idAnggota;
        this.idBuku = idBuku;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        this.status = status;
        this.denda = denda;
    }

    public int getIdLog() { return idLog; }
    public String getIdAnggota() { return idAnggota; }
    public String getIdBuku() { return idBuku; }
    public Date getTglPinjam() { return tglPinjam; }
    public Date getTglKembali() { return tglKembali; }
    public String getStatus() { return status; }
    public double getDenda() { return denda; }
}