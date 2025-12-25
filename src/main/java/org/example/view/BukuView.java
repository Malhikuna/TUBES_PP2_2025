package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BukuView extends JFrame {
    // Komponen Header
    private JRadioButton rbSemua, rbDipinjam, rbKembali;
    private JTextField txtSearch;
    private ButtonGroup statusGroup;

    // Komponen Form Input
    private JTextField txtIdBuku, txtJudul, txtPengarang, txtKategori;
    private JSpinner spnStok;
    
    // Tabel
    private JTable tabelBuku;
    private DefaultTableModel tableModel;

    // Tombol
    private JButton btnTambah, btnUbah, btnHapus, btnClear;

    public BukuView() {
        setTitle("Manajemen Data Buku");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // HEADER 
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHeader.setBorder(BorderFactory.createTitledBorder("Filter & Pencarian"));
        
        rbSemua = new JRadioButton("Semua", true);
        rbDipinjam = new JRadioButton("Sedang Dipinjam");
        rbKembali = new JRadioButton("Sudah Kembali");
        statusGroup = new ButtonGroup();
        statusGroup.add(rbSemua);
        statusGroup.add(rbDipinjam);
        statusGroup.add(rbKembali);

        txtSearch = new JTextField(20);
        
        panelHeader.add(new JLabel("Status: "));
        panelHeader.add(rbSemua);
        panelHeader.add(rbDipinjam);
        panelHeader.add(rbKembali);
        panelHeader.add(new JLabel(" | Cari Judul: "));
        panelHeader.add(txtSearch);

        // FORM INPUT 
        JPanel panelInput = new JPanel(new GridLayout(6, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Form Input Buku"));
        
        txtIdBuku = new JTextField();
        txtIdBuku.setEditable(false); 
        txtIdBuku.setBackground(Color.LIGHT_GRAY);
        
        txtJudul = new JTextField();
        txtPengarang = new JTextField();
        txtKategori = new JTextField();
        spnStok = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));

        panelInput.add(new JLabel("ID Buku:"));
        panelInput.add(txtIdBuku);
        panelInput.add(new JLabel("Judul Buku:"));
        panelInput.add(txtJudul);
        panelInput.add(new JLabel("Pengarang:"));
        panelInput.add(txtPengarang);
        panelInput.add(new JLabel("Kategori:"));
        panelInput.add(txtKategori);
        panelInput.add(new JLabel("Stok:"));
        panelInput.add(spnStok);

        JPanel panelTombol = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        panelTombol.add(btnTambah);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnClear);

        JPanel panelKiri = new JPanel(new BorderLayout());
        panelKiri.add(panelInput, BorderLayout.NORTH);
        panelKiri.add(panelTombol, BorderLayout.CENTER);

        // TABEL 
        String[] kolom = {"ID Log", "Nama Peminjam", "Judul Buku", "Tgl Pinjam", "Tgl Kembali", "Status"};
        tableModel = new DefaultTableModel(kolom, 0);
        tabelBuku = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelBuku);

        add(panelHeader, BorderLayout.NORTH);
        add(panelKiri, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // LOGIKA TOMBOL 
        btnTambah.addActionListener(e -> validasiInput());
        btnUbah.addActionListener(e -> aksiUbah());
        btnHapus.addActionListener(e -> aksiHapus());
        btnClear.addActionListener(e -> clearForm());
    }

    // METHOD-METHOD LOGIKA 

    private void clearForm() {
        txtIdBuku.setText("");
        txtJudul.setText("");
        txtPengarang.setText("");
        txtKategori.setText("");
        spnStok.setValue(0);
        rbSemua.setSelected(true);
    }

    private void validasiInput() {
        if (txtJudul.getText().isEmpty() || txtPengarang.getText().isEmpty() || txtKategori.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Peringatan: Field tidak boleh kosong!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
        } else {
            // Logika Tambah ke Tabel
            tableModel.addRow(new Object[]{
                tableModel.getRowCount() + 1, "-", txtJudul.getText(), "-", "-", "Tersedia"
            });
            clearForm();
        }
    }

    private void aksiUbah() {
        int baris = tabelBuku.getSelectedRow();
        if (baris != -1) {
            tableModel.setValueAt(txtJudul.getText(), baris, 2);
            JOptionPane.showMessageDialog(this, "Data Berhasil Diubah!");
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris di tabel dulu!");
        }
    }

    private void aksiHapus() {
        int baris = tabelBuku.getSelectedRow();
        if (baris != -1) {
            tableModel.removeRow(baris);
            JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus!");
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris di tabel dulu!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BukuView().setVisible(true));
    }
}