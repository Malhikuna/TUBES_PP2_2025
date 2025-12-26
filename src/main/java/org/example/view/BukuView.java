package org.example.view;

import org.example.controller.BukuController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BukuView extends JPanel {
    public DefaultTableModel model;
    public JTable table;
    public JTextField txtIdBuku, txtJudul, txtPengarang, txtKategori, txtCari;
    public JSpinner spnStok;
    public JRadioButton rbSemua, rbDipinjam, rbKembali;
    public JButton btnTambah, btnUbah, btnHapus, btnClear;
    private BukuController controller;

    public BukuView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        this.controller = new BukuController(this);
    }

    private void initComponents() {
        // Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbSemua = new JRadioButton("Semua", true);
        rbDipinjam = new JRadioButton("Sedang Dipinjam");
        rbKembali = new JRadioButton("Sudah Kembali");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSemua); bg.add(rbDipinjam); bg.add(rbKembali);
        
        pnlFilter.add(new JLabel("Status: "));
        pnlFilter.add(rbSemua); pnlFilter.add(rbDipinjam); pnlFilter.add(rbKembali);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCari = new JTextField(15);
        pnlSearch.add(new JLabel("Cari Judul: "));
        pnlSearch.add(txtCari);
        pnlHeader.add(pnlFilter, BorderLayout.WEST);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);

        // Tabel sesuai kriteria (ID Log | Nama Peminjam | Judul Buku | Tgl Pinjam | Tgl Kembali | Status)
        model = new DefaultTableModel(new String[]{"ID Log", "Nama Peminjam", "Judul Buku", "Tgl Pinjam", "Tgl Kembali", "Status"}, 0);
        table = new JTable(model);

        // Form Input
        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlInput.setPreferredSize(new Dimension(300, 200));
        txtIdBuku = new JTextField("Auto"); txtIdBuku.setEditable(false);
        txtJudul = new JTextField();
        txtPengarang = new JTextField();
        txtKategori = new JTextField();
        spnStok = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));

        pnlInput.add(new JLabel("ID Buku:")); pnlInput.add(txtIdBuku);
        pnlInput.add(new JLabel("Judul:")); pnlInput.add(txtJudul);
        pnlInput.add(new JLabel("Pengarang:")); pnlInput.add(txtPengarang);
        pnlInput.add(new JLabel("Kategori:")); pnlInput.add(txtKategori);
        pnlInput.add(new JLabel("Stok:")); pnlInput.add(spnStok);

        // Buttons
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        pnlButtons.add(btnTambah); pnlButtons.add(btnUbah); 
        pnlButtons.add(btnHapus); pnlButtons.add(btnClear);

        add(pnlHeader, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(pnlInput, BorderLayout.WEST);
        add(pnlButtons, BorderLayout.SOUTH);
    }
}