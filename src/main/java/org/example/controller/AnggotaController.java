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
        loadDataAnggota("", "ASC");;

    }


    private void listener() {
        // Tombol TAMBAH
        view.btnTambah.addActionListener(e -> {
            String nama = view.txtNama.getText();
            String telp = view.txtTelp.getText();
            String status = view.rbAktif.isSelected() ? "Aktif" : "Tidak Aktif";
            
            tambahAnggota(null, nama, telp, status);
            loadDataAnggota("", "ASC");
            clearForm(); 
        });

        // Tombol UBAH
        view.btnUbah.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row != -1) {
                String id = view.model.getValueAt(row, 0).toString();
                ubahAnggota(id, view.txtNama.getText(), view.txtTelp.getText(), 
                            view.rbAktif.isSelected() ? "Aktif" : "Tidak Aktif");
                loadDataAnggota("", "ASC");
                clearForm();
            }
        });

        // Tombol HAPUS
        view.btnHapus.addActionListener(e -> {
            int row = view.table.getSelectedRow();
            if (row != -1) {
                String id = view.model.getValueAt(row, 0).toString();
                hapusAnggota(id);
                loadDataAnggota("", "ASC");
                clearForm();
            }
        });

        // Tombol CLEAR
        view.btnClear.addActionListener(e -> clearForm());
        
        // Sinkronisasi Tabel ke Form 
        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.table.getSelectedRow() != -1) {
                int row = view.table.getSelectedRow();
                view.txtId.setText(view.model.getValueAt(row, 0).toString());
                view.txtNama.setText(view.model.getValueAt(row, 1).toString());
                view.txtTelp.setText(view.model.getValueAt(row, 2).toString());
            }
        });
    }
    private void clearForm() {
        view.txtId.setText("Auto");
        view.txtNama.setText("");
        view.txtTelp.setText("");
        view.rbSemua.setSelected(true);
        view.table.clearSelection();
    }


    public void loadDataAnggota(String kataKunci, String sortOrder) {
        DefaultTableModel tableModel = view.model;
        tableModel.setRowCount(0);
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            conn = KoneksiDB.configDB();
            StringBuilder sql = new StringBuilder("SELECT * FROM anggota");

            if (kataKunci != null && !kataKunci.isEmpty()) {
                sql.append(" WHERE nama LIKE ? OR id_anggota LIKE ?");
            }

            if (sortOrder != null && !sortOrder.isEmpty()) {
                sql.append(" ORDER BY status_aktif ").append(sortOrder);
            }

            pst = conn.prepareStatement(sql.toString());

            if (kataKunci != null && !kataKunci.isEmpty()) {
                pst.setString(1, "%" + kataKunci + "%");
                pst.setString(2, "%" + kataKunci + "%");
            }

            res = pst.executeQuery();
            while (res.next()) {
                tableModel.addRow(new Object[]{
                        res.getString("id_anggota"),
                res.getString("nama"),
                res.getString("no_telp"),
                res.getString("status_aktif")
            });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal Load Data Anggota: " + e.getMessage());
        }
    }

    private void cariData() {
//        String kataKunci = view.txtCari.getText();
//        String sortOrder = view.checkSort.isSelected() ? "DESC" : "ASC";
//        loadDataAnggpota(kataKunci, sortOrder);
//         ini tinggal pake ya
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