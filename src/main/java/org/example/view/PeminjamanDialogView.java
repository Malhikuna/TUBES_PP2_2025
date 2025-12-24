package org.example.view;

import org.example.KoneksiDB;
import org.example.controller.TransaksiController;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class PeminjamanDialogView extends JDialog {
    private JComboBox<String> cbAnggota, cbBuku;
    private TransaksiController transaksiController;

    public PeminjamanDialogView(Frame owner, TransaksiController controller) {
        super(owner, "Tambah Peminjaman Baru", true);
        this.transaksiController = controller;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cbAnggota = new JComboBox<>();
        cbBuku = new JComboBox<>();
        loadComboData();

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Pilih Anggota:"), gbc);
        gbc.gridx = 1; add(cbAnggota, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Pilih Buku:"), gbc);
        gbc.gridx = 1; add(cbBuku, gbc);

        JButton btnSimpan = new JButton("Simpan");
        gbc.gridx = 1; gbc.gridy = 3; add(btnSimpan, gbc);

        btnSimpan.addActionListener(e -> {
            if (cbAnggota.getSelectedItem() != null && cbBuku.getSelectedItem() != null) {
                String idAnggota = cbAnggota.getSelectedItem().toString().split(" - ")[0];
                String idBuku = cbBuku.getSelectedItem().toString().split(" - ")[0];
                transaksiController.pinjamBuku(idAnggota, idBuku);
                dispose();
            }
        });
    }

    private void loadComboData() {
        try (Connection conn = KoneksiDB.configDB()) {
            ResultSet rsA = conn.createStatement().executeQuery("SELECT id_anggota, nama FROM anggota");
            while(rsA.next()) {
                cbAnggota.addItem(rsA.getString("id_anggota") + " - " + rsA.getString("nama"));
            }

            ResultSet rsB = conn.createStatement().executeQuery("SELECT id_buku, judul FROM buku WHERE stok > 0");
            while(rsB.next()) {
                cbBuku.addItem(rsB.getString("id_buku") + " - " + rsB.getString("judul"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }
}

