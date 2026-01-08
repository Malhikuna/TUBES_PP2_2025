package org.example.model;

public class BukuModel {
    private String idBuku, judul, pengarang, kategori;
    private int stok;

    public BukuModel(String idBuku, String judul, String pengarang, String kategori, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.pengarang = pengarang;
        this.kategori = kategori;
        this.stok = stok;
    }

    public String getIdBuku() { return idBuku; }
    public String getJudul() { return judul; }
    public String getPengarang() { return pengarang; }
    public String getKategori() { return kategori; }
    public int getStok() { return stok; }
}