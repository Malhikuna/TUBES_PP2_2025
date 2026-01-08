package org.example.controller;

import org.example.KoneksiDB;
import org.example.view.PeminjamanDialogView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class PeminjamanDialogController {

    private final PeminjamanDialogView view;
    private final TransaksiController transaksiController;
    private final HashMap<String, String> mapAnggota = new HashMap<>();
    private final HashMap<String, String> mapBuku = new HashMap<>();

    public PeminjamanDialogController(PeminjamanDialogView view, TransaksiController transaksiController) {
        this.view = view;
        this.transaksiController = transaksiController;

        loadDataComboBox();

        this.view.setSimpanListener(e -> actionSimpan());
        this.view.setBatalListener(e -> view.dispose());
    }

    private void loadDataComboBox() {
        try (Connection conn = KoneksiDB.configDB()) {
            Statement st = conn.createStatement();

            ResultSet rsA = st.executeQuery("SELECT id_anggota, nama FROM anggota");
            while (rsA.next()) {
                String nama = rsA.getString("nama");
                String id = rsA.getString("id_anggota");
                mapAnggota.put(nama, id);
                view.addAnggotaItem(nama);
            }

            ResultSet rsB = st.executeQuery("SELECT id_buku, judul FROM buku WHERE stok > 0");
            while (rsB.next()) {
                String judul = rsB.getString("judul");
                String id = rsB.getString("id_buku");
                mapBuku.put(judul, id);
                view.addBukuItem(judul);
            }

        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Gagal memuat data: " + e.getMessage());
        }
    }

    private void actionSimpan() {
        String selectedAnggota = view.getSelectedAnggota();
        String selectedBuku = view.getSelectedBuku();
        String durasiStr = view.getDurasi();

        if (selectedAnggota == null || selectedBuku == null) {
            view.showMessage("Harap pilih Anggota dan Buku!");
            return;
        }

        String idAnggota = mapAnggota.get(selectedAnggota);
        String idBuku = mapBuku.get(selectedBuku);

        try {
            int durasi = Integer.parseInt(durasiStr);

            if (transaksiController.isBukuSedangDipinjam(idAnggota, idBuku)) {
                view.showWarning("Anggota ini masih meminjam buku '" + selectedBuku + "' dan belum dikembalikan!");
                return;
            }

            transaksiController.pinjamBuku(idAnggota, idBuku, durasi);
            view.dispose();

        } catch (NumberFormatException ex) {
            view.showMessage("Durasi harus berupa angka!");
        }
    }
}