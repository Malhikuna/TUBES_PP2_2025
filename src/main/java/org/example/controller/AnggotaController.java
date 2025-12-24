package org.example.controller;


import org.example.KoneksiDB;
import org.example.view.AnggotaView;


import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AnggotaController {

    private AnggotaView view;

    public AnggotaController(AnggotaView view) {
        this.view = view;

        listener();
        loadDataAnggota();

    }


    private void listener() {
// isi disini gess
    }






    public DefaultTableModel loadDataAnggota() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Anggota");
        model.addColumn("Nama");
        model.addColumn("No Telp");
        model.addColumn("Status");

        try {
            Connection conn = KoneksiDB.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT * FROM anggota");

            while (res.next()) {
                model.addRow(new Object[]{
                        res.getString("id_anggota"),
                        res.getString("nama"),
                        res.getString("no_telp"),
                        res.getString("status_aktif")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Load Data: " + e.getMessage());
        }
        return model;
    }

    public void tambahAnggota(String id, String nama, String telp, String status) {
        try {
            String sql = "INSERT INTO anggota (id_anggota, nama, no_telp, status_aktif) VALUES (?, ?, ?, ?)";
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, nama);
            pst.setString(3, telp);
            pst.setString(4, status);

            pst.execute();
            JOptionPane.showMessageDialog(null, "Anggota Berhasil Disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Simpan: " + e.getMessage());
        }
    }

    public void ubahAnggota(String id, String nama, String telp, String status) {
        try {
            String sql = "UPDATE anggota SET nama=?, no_telp=?, status_aktif=? WHERE id_anggota=?";
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, nama);
            pst.setString(2, telp);
            pst.setString(3, status);
            pst.setString(4, id);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Anggota Berhasil Diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Edit: " + e.getMessage());
        }
    }

    public void hapusAnggota(String id) {
        try {
            String sql = "DELETE FROM anggota WHERE id_anggota=?";
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, id);

            pst.execute();
            JOptionPane.showMessageDialog(null, "Anggota Berhasil Dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage());
        }
    }
}