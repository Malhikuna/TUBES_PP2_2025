package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class PeminjamanDialogView extends JDialog {
    private JComboBox<String> cbAnggota, cbBuku;
    private JTextField txtTgl, txtDurasi;
    private JButton btnSimpan, btnBatal;

    public PeminjamanDialogView(Frame owner) {
        super(owner, "Tambah Peminjaman Baru", true);
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

        btnSimpan = new JButton("Simpan");
        btnBatal = new JButton("Batal");
        add(btnSimpan);
        add(btnBatal);
    }


    public void addAnggotaItem(String nama) {
        cbAnggota.addItem(nama);
    }

    public void addBukuItem(String judul) {
        cbBuku.addItem(judul);
    }

    public String getSelectedAnggota() {
        return (String) cbAnggota.getSelectedItem();
    }

    public String getSelectedBuku() {
        return (String) cbBuku.getSelectedItem();
    }

    public String getDurasi() {
        return txtDurasi.getText();
    }

    public void setSimpanListener(ActionListener listener) {
        btnSimpan.addActionListener(listener);
    }

    public void setBatalListener(ActionListener listener) {
        btnBatal.addActionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }
}