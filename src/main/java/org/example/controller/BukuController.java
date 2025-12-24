
package org.example.controller;

import org.example.KoneksiDB;
import org.example.view.BukuView;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BukuController {


    private BukuView view;

    public BukuController(BukuView view) {
        this.view = view;

        listener();
        loadDataBuku();

    }


    private void listener() {
// isi disini gess
    }



    public DefaultTableModel loadDataBuku() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Buku");
        model.addColumn("Judul");
        model.addColumn("Pengarang");
        model.addColumn("Kategori");
        model.addColumn("Stok");

        try {
            Connection conn = KoneksiDB.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT * FROM buku");

            while (res.next()) {
                model.addRow(new Object[]{
                        res.getString("id_buku"),
                        res.getString("judul"),
                        res.getString("pengarang"),
                        res.getString("kategori"),
                        res.getInt("stok")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Load Data: " + e.getMessage());
        }
        return model;
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