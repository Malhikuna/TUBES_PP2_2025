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
    public JButton btnTambah, btnUbah, btnHapus, btnClear;
    public JCheckBox checkSort;
    public JLabel lblError;

    public BukuView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initHeader();

        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));

        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 10)); 
        pnlInput.setBorder(BorderFactory.createTitledBorder("Form Input Data Buku"));
        
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

        model = new DefaultTableModel(new String[]{"No", "ID Buku", "Judul", "Pengarang", "Kategori", "Stok"}, 0);
        table = new JTable(model);
        
        pnlCenter.add(pnlInput, BorderLayout.NORTH);
        pnlCenter.add(new JScrollPane(table), BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        initButtons();

        BukuController controller = new BukuController(this);
    }

    private void initHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCari = new JTextField(15);

        pnlSearch.add(new JLabel("Cari Judul: "));
        pnlSearch.add(txtCari);
        
        pnlHeader.add(pnlSearch, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);
    }

    private void initButtons() {
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        pnlButtons.add(btnTambah); pnlButtons.add(btnUbah); 
        pnlButtons.add(btnHapus); pnlButtons.add(btnClear);
        add(pnlButtons, BorderLayout.SOUTH);
    }
}
