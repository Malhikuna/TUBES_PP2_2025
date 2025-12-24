package org.example.controller;

import org.example.KoneksiDB;
import org.example.view.TransaksiView;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TransaksiController {

    private TransaksiView view;

    public TransaksiController(TransaksiView view) {
        this.view = view;
        listener();
        loadDataTransaksi(null, "Semua");
    }

    private void listener() {
    }

    public DefaultTableModel loadDataTransaksi(String keyword, String statusFilter) {
        DefaultTableModel model = new DefaultTableModel();
        // Setup Header Kolom
        model.addColumn("ID Log");
        model.addColumn("Nama Anggota");
        model.addColumn("Judul Buku");
        model.addColumn("Tgl Pinjam");
        model.addColumn("Tgl Kembali");
        model.addColumn("Status");
        model.addColumn("Denda");

        try {
            Connection conn = KoneksiDB.configDB();

            StringBuilder sql = new StringBuilder(
                    "SELECT p.id_log, a.nama, b.judul, p.tgl_pinjam, p.tgl_kembali, p.status, p.denda " +
                            "FROM peminjaman p " +
                            "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                            "JOIN buku b ON p.id_buku = b.id_buku " +
                            "WHERE 1=1 "
            );

            if (keyword != null && !keyword.isEmpty()) {
                sql.append("AND (a.nama LIKE '%").append(keyword).append("%' ")
                        .append("OR b.judul LIKE '%").append(keyword).append("%') ");
            }

            if (statusFilter != null && !statusFilter.equalsIgnoreCase("Semua")) {
                sql.append("AND p.status = '").append(statusFilter).append("' ");
            }

            sql.append("ORDER BY p.id_log DESC");

            // E. Eksekusi
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql.toString());

            while (res.next()) {
                model.addRow(new Object[]{
                        res.getInt("id_log"),
                        res.getString("nama"),
                        res.getString("judul"),
                        res.getDate("tgl_pinjam"),
                        res.getDate("tgl_kembali"),
                        res.getString("status"),
                        res.getDouble("denda")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Load Transaksi: " + e.getMessage());
        }
        return model;
    }

    public DefaultTableModel filterByStatus(String status) {
        // Panggil mesin utama, keyword dianggap null (kosong)
        return loadDataTransaksi(null, status);
    }

    public void pinjamBuku(String idAnggota, String idBuku) {
        Connection conn = null;
        try {
            conn = KoneksiDB.configDB();
            conn.setAutoCommit(false);

            String sqlCek = "SELECT stok FROM buku WHERE id_buku = ?";
            PreparedStatement pstCek = conn.prepareStatement(sqlCek);
            pstCek.setString(1, idBuku);
            ResultSet rs = pstCek.executeQuery();

            if (rs.next()) {
                int stok = rs.getInt("stok");
                if (stok > 0) {
                    String sqlPinjam = "INSERT INTO peminjaman (id_anggota, id_buku, tgl_pinjam, status) VALUES (?, ?, CURDATE(), 'Dipinjam')";
                    PreparedStatement pstPinjam = conn.prepareStatement(sqlPinjam);
                    pstPinjam.setString(1, idAnggota);
                    pstPinjam.setString(2, idBuku);
                    pstPinjam.executeUpdate();

                    String sqlStok = "UPDATE buku SET stok = stok - 1 WHERE id_buku = ?";
                    PreparedStatement pstStok = conn.prepareStatement(sqlStok);
                    pstStok.setString(1, idBuku);
                    pstStok.executeUpdate();

                    conn.commit();
                    JOptionPane.showMessageDialog(null, "Peminjaman Berhasil! Stok berkurang.");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal: Stok Buku Habis!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Kode Buku tidak ditemukan.");
            }

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {} // Batalkan jika error
            JOptionPane.showMessageDialog(null, "Gagal Pinjam: " + e.getMessage());
        }
    }

    public void kembalikanBuku(int idLog) {
        Connection conn = null;
        try {
            conn = KoneksiDB.configDB();
            conn.setAutoCommit(false);

            String sqlGet = "SELECT tgl_pinjam, id_buku FROM peminjaman WHERE id_log = ? AND status = 'Dipinjam'";
            PreparedStatement pstGet = conn.prepareStatement(sqlGet);
            pstGet.setInt(1, idLog);
            ResultSet rs = pstGet.executeQuery();

            if (rs.next()) {
                Date tglPinjamSQL = rs.getDate("tgl_pinjam");
                String idBuku = rs.getString("id_buku");

                LocalDate tglPinjam = tglPinjamSQL.toLocalDate();
                LocalDate tglKembali = LocalDate.now(); // Hari ini
                LocalDate jatuhTempo = tglPinjam.plusDays(7); // Batas 7 hari

                long denda = 0;
                long hariTelat = ChronoUnit.DAYS.between(jatuhTempo, tglKembali);

                if (hariTelat > 0) {
                    denda = hariTelat * 2000; // Rp 2.000 per hari
                }

                String sqlUpdate = "UPDATE peminjaman SET tgl_kembali = ?, status = 'Kembali', denda = ? WHERE id_log = ?";
                PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
                pstUpdate.setDate(1, Date.valueOf(tglKembali));
                pstUpdate.setDouble(2, denda);
                pstUpdate.setInt(3, idLog);
                pstUpdate.executeUpdate();

                String sqlStok = "UPDATE buku SET stok = stok + 1 WHERE id_buku = ?";
                PreparedStatement pstStok = conn.prepareStatement(sqlStok);
                pstStok.setString(1, idBuku);
                pstStok.executeUpdate();

                conn.commit();

                String pesan = "Buku Dikembalikan.\nDenda: Rp " + denda;
                JOptionPane.showMessageDialog(null, pesan);
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau buku sudah dikembalikan.");
            }

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "Gagal Mengembalikan: " + e.getMessage());
        }
    }
}