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

        loadDataCB();

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

    private void loadDataCB() {
        try (Connection conn = KoneksiDB.configDB()) {
            Statement stm = conn.createStatement();

            ResultSet rsA = stm.executeQuery("SELECT id_anggota, nama FROM anggota");
            while (rsA.next()) {
                String item = rsA.getString("nama");
                cbAnggota.addItem(item);
                mapAnggota.put(item, rsA.getString("id_anggota"));
            }

            cbKategori.removeAllItems();
            cbKategori.addItem("Semua");
            ResultSet rsK = stm.executeQuery("SELECT DISTINCT kategori FROM buku WHERE stok > 0 ORDER BY kategori ASC");
            while (rsK.next()) {
                String kat = rsK.getString("kategori");
                if (kat != null && !kat.isEmpty()) {
                    cbKategori.addItem(kat);
                }
            }

            loadBukuByKategori("Semua");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBukuByKategori(String kategori) {
        cbBuku.removeAllItems();
        mapBuku.clear();

        try (Connection conn = KoneksiDB.configDB()) {
            String sql;
            PreparedStatement pst;

            if (kategori == null || kategori.equals("Semua")) {
                sql = "SELECT id_buku, judul FROM buku WHERE stok > 0";
                pst = conn.prepareStatement(sql);
            } else {
                sql = "SELECT id_buku, judul FROM buku WHERE stok > 0 AND kategori = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, kategori);
            }

            ResultSet rsB = pst.executeQuery();
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