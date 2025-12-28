
package org.example.controller;

import org.example.KoneksiDB;
import org.example.util.GeneratorID;
import org.example.view.BukuView;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BukuController {


    private BukuView view;

    public BukuController(BukuView view) {
        this.view = view;

        listener();
        loadDataBuku(null);

    }


    private void listener() {
        // 1. Aksi Tombol TAMBAH
        view.btnTambah.addActionListener(e -> {
            String judul = view.txtJudul.getText();
            String pengarang = view.txtPengarang.getText();
            String kategori = view.txtKategori.getText();
            int stok = (int) view.spnStok.getValue();

            if (judul.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Judul tidak boleh kosong!");
                return;
            }

            tambahBuku(GeneratorID.generateBuku(view.model), judul, pengarang, kategori, stok);
            loadDataBuku(null);
            clearForm();
        });

        // 2. Aksi Tombol UBAH
        view.btnUbah.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row != -1) {
                String id = view.model.getValueAt(row, 1).toString();
                String judul = view.txtJudul.getText();
                String pengarang = view.txtPengarang.getText();
                String kategori = view.txtKategori.getText();
                int stok = (int) view.spnStok.getValue();

                ubahBuku(id, judul, pengarang, kategori, stok);
                loadDataBuku(null);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Pilih baris yang ingin diubah!");
            }
        });

        // 3. Aksi Tombol HAPUS
        view.btnHapus.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row != -1) {
                String id = view.model.getValueAt(row, 1).toString();
                int confirm = JOptionPane.showConfirmDialog(view, "Hapus buku ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    hapusBuku(id);
                    loadDataBuku(null);
                    clearForm();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Pilih baris yang ingin dihapus!");
            }
        });

        // 4. Aksi Tombol CLEAR
        view.btnClear.addActionListener(e -> clearForm());

        // 5. Sinkronisasi Tabel ke Form
        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.table.getSelectedRow() != -1) {
                int row = view.table.getSelectedRow();
                view.txtIdBuku.setText(view.model.getValueAt(row, 1).toString());
                view.txtJudul.setText(view.model.getValueAt(row, 2).toString());
                view.txtPengarang.setText(view.model.getValueAt(row, 3).toString());
                view.txtKategori.setText(view.model.getValueAt(row, 4).toString());
                view.spnStok.setValue(Integer.parseInt(view.model.getValueAt(row, 5).toString()));
            }
        });

        // 6. Pencarian Real-time
        view.txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loadDataBuku(view.txtCari.getText());
            }
        });
    }
        private void clearForm() {
            view.txtIdBuku.setText(GeneratorID.generateBuku(view.model));
            view.txtJudul.setText("");
            view.txtPengarang.setText("");
            view.txtKategori.setText("");
            view.spnStok.setValue(0);
            view.table.clearSelection();
}

    public void loadDataBuku(String kataKunci) {
        DefaultTableModel tableModel = view.model;
        tableModel.setRowCount(0);
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            conn = KoneksiDB.configDB();
            String sql;

            if (kataKunci == null || kataKunci.isEmpty()) {
                sql = "SELECT * FROM buku";
                pst = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM buku WHERE judul LIKE ? OR pengarang LIKE ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, "%" + kataKunci + "%");
            }


            res = pst.executeQuery();



            int no = 1;
            while (res.next()) {
                tableModel.addRow(new Object[]{
                        no++,
                        res.getString("id_buku"),
                res.getString("judul"),
                res.getString("pengarang"),
                res.getString("kategori"),
                res.getInt("stok")
            });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal Load Data Buku: " + e.getMessage());
        } finally {
            try { if (res != null) res.close(); } catch (Exception e) {}
            try { if (pst != null) pst.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }


    private void cariData() {
//        String kataKunci = view.txtCari.getText();
//        loadDataBuku(kataKunci);
//        tinngal pake aja ya gaess

    }

    public void tambahBuku(String id, String judul, String pengarang, String kategori, int stok) {
        try {
            String sql = "INSERT INTO buku VALUES (?, ?, ?, ?, ?)";
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, judul);
            pst.setString(3, pengarang);
            pst.setString(4, kategori);
            pst.setInt(5, stok);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Simpan: " + e.getMessage());
        }
    }

    public void ubahBuku(String id, String judul, String pengarang, String kategori, int stok) {
        try {
            String sql = "UPDATE buku SET judul=?, pengarang=?, kategori=?, stok=? WHERE id_buku=?";
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, judul);
            pst.setString(2, pengarang);
            pst.setString(3, kategori);
            pst.setInt(4, stok);
            pst.setString(5, id); // Kunci update
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Edit: " + e.getMessage());
        }
    }

    public void hapusBuku(String id) {
        try {
            String sql = "DELETE FROM buku WHERE id_buku=?";
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage());
        }
    }
}