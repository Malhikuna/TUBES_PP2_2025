package org.example.view;

import org.example.KoneksiDB;
import org.example.controller.TransaksiController;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class PeminjamanDialogView extends JDialog {
    private JComboBox<String> cbAnggota, cbBuku;
    private JTextField txtTgl, txtKategori, txtDurasi;
    private HashMap<String, String> mapAnggota = new HashMap<>();
    private HashMap<String, String> mapBuku = new HashMap<>();
    private TransaksiController controller;

    public PeminjamanDialogView(Frame owner, TransaksiController controller) {
        super(owner, "Tambah Peminjaman Baru", true);
        this.controller = controller;
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel(" Pilih Anggota:"));
        cbAnggota = new JComboBox<>();
        add(cbAnggota);

        add(new JLabel(" Pilih Buku:"));
        cbBuku = new JComboBox<>();
        add(cbBuku);

        add(new JLabel(" Tanggal Pinjam:"));
        txtTgl = new JTextField(LocalDate.now().toString());
        txtTgl.setEditable(false);
        add(txtTgl);

        add(new JLabel(" Durasi (Hari):"));
        txtDurasi = new JTextField("7");
        add(txtDurasi);

        JButton btnSimpan = new JButton("Simpan");
        JButton btnBatal = new JButton("Batal");
        add(btnSimpan);
        add(btnBatal);

        loadDataCB();

        btnSimpan.addActionListener(e -> {
            String idA = mapAnggota.get(cbAnggota.getSelectedItem().toString());
            String idB = mapBuku.get(cbBuku.getSelectedItem().toString());

            int durasi = Integer.parseInt(txtDurasi.getText()); //

            controller.pinjamBuku(idA, idB, durasi);
            dispose();
        });

        btnBatal.addActionListener(e -> dispose());
    }

    private void loadDataCB() {
        try (Connection conn = KoneksiDB.configDB()) {
            ResultSet rsA = conn.createStatement().executeQuery("SELECT id_anggota, nama FROM anggota");
            while (rsA.next()) {
                String item = rsA.getString("nama");
                cbAnggota.addItem(item);
                mapAnggota.put(item, rsA.getString("id_anggota"));
            }
            ResultSet rsB = conn.createStatement().executeQuery("SELECT id_buku, judul FROM buku WHERE stok > 0");
            while (rsB.next()) {
                String item = rsB.getString("judul");
                cbBuku.addItem(item);
                mapBuku.put(item, rsB.getString("id_buku"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}