package org.example.view;

import org.example.KoneksiDB;
import org.example.controller.TransaksiController;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class PeminjamanDialogView extends JDialog {
    private JComboBox<String> cbAnggota, cbKategori, cbBuku;
    private JTextField txtTgl, txtDurasi;
    private HashMap<String, String> mapAnggota = new HashMap<>();
    private HashMap<String, String> mapBuku = new HashMap<>();
    private TransaksiController controller;

    public PeminjamanDialogView(Frame owner, TransaksiController controller) {
        super(owner, "Tambah Peminjaman Baru", true);
        this.controller = controller;
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel(" Pilih Anggota:"));
        cbAnggota = new JComboBox<>();
        add(cbAnggota);

        add(new JLabel("Pilih Kategori:"));
        cbKategori = new JComboBox<>();
        add(cbKategori);

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


        cbKategori.addActionListener(e -> {
            String kategoriTerpilih = (String) cbKategori.getSelectedItem();
            loadBukuByKategori(kategoriTerpilih);
        });

        if (cbBuku.getSelectedItem() == null || cbAnggota.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Anggota dan Buku harus dipilih!");
            return;
        }

        btnSimpan.addActionListener(e -> {
            String idA = mapAnggota.get(cbAnggota.getSelectedItem().toString());
            String idB = mapBuku.get(cbBuku.getSelectedItem().toString());
            String judulBuku = cbBuku.getSelectedItem().toString();

            try{
                int durasi = Integer.parseInt(txtDurasi.getText());

                if (controller.isBukuSedangDipinjam(idA, idB)) {
                    JOptionPane.showMessageDialog(this,
                            "Anggota ini masih meminjam buku '" + judulBuku + "' dan belum dikembalikan!",
                            "Peringatan",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                controller.pinjamBuku(idA, idB, durasi);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Durasi harus berupa angka!");
            }
        });

        btnBatal.addActionListener(e -> {
            dispose();
        });
    }
}