package org.example.view;

import org.example.controller.TransaksiController;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.text.MessageFormat;
import java.awt.print.PrinterException;

public class PeminjamanView extends JPanel {
    private JTable tableLog;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTextField txtSearch;
    private JRadioButton rbSemua, rbDipinjam, rbKembali;
    private JButton btnPinjamBaru, btnKembalikan, btnHapus, btnCetak;
    private TransaksiController transaksiController;

    public PeminjamanView() {
        this.transaksiController = new TransaksiController(this);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initHeader();
        initTable();
        initButtons();
        initFilters();
        setupSelectionListener();

        refreshTable();
    }

    private void initHeader() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));

        rbSemua = new JRadioButton("Semua", true);
        rbDipinjam = new JRadioButton("Sedang Dipinjam");
        rbKembali = new JRadioButton("Sudah Kembali");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSemua); bg.add(rbDipinjam); bg.add(rbKembali);

        pnlStatus.add(new JLabel("Status: "));
        pnlStatus.add(rbSemua); pnlStatus.add(rbDipinjam); pnlStatus.add(rbKembali);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtSearch = new JTextField(15);
        pnlSearch.add(new JLabel("Cari Peminjam: "));
        pnlSearch.add(txtSearch);

        pnlHeader.add(pnlStatus, BorderLayout.WEST);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);
    }

    private void initTable() {
        tableModel = new DefaultTableModel(new String[]{"ID Log", "Peminjam", "Buku", "Tgl Pinjam", "Tgl Wajib Kembali", "Status", "Denda"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableLog = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        tableLog.setRowSorter(rowSorter);
        add(new JScrollPane(tableLog), BorderLayout.CENTER);
    }

    public void refreshTable() {
        DefaultTableModel modelFromDb = transaksiController.loadDataTransaksi(null, "Semua");
        tableModel.setRowCount(0);

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (int i = 0; i < modelFromDb.getRowCount(); i++) {
            Object[] row = new Object[7];
            row[0] = modelFromDb.getValueAt(i, 0);
            row[1] = modelFromDb.getValueAt(i, 1);
            row[2] = modelFromDb.getValueAt(i, 2);
            row[3] = modelFromDb.getValueAt(i, 3);
            row[4] = modelFromDb.getValueAt(i, 4);
            row[5] = modelFromDb.getValueAt(i, 5);

            Object dendaObj = modelFromDb.getValueAt(i, 6);
            double dendaVal = (dendaObj != null) ? Double.parseDouble(dendaObj.toString()) : 0;
            row[6] = nf.format(dendaVal);

            tableModel.addRow(row);
        }
    }

    private void initFilters() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyFilters(); }
            public void removeUpdate(DocumentEvent e) { applyFilters(); }
            public void changedUpdate(DocumentEvent e) { applyFilters(); }
        });

        rbSemua.addActionListener(e -> applyFilters());
        rbDipinjam.addActionListener(e -> applyFilters());
        rbKembali.addActionListener(e -> applyFilters());
    }

    private void applyFilters() {
        java.util.List<RowFilter<Object, Object>> filters = new ArrayList<>();
        String text = txtSearch.getText();
        if (!text.trim().isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + text, 1));
        }
        if (rbDipinjam.isSelected()) {
            filters.add(RowFilter.regexFilter("^Dipinjam$", 5));
        } else if (rbKembali.isSelected()) {
            filters.add(RowFilter.regexFilter("^Kembali$", 5));
        }
        rowSorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private void initButtons() {
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPinjamBaru = new JButton("+ Pinjam Baru");
        btnKembalikan = new JButton("Kembalikan Buku");
        btnHapus = new JButton("Hapus Log");
        btnCetak = new JButton("Export PDF");
        btnCetak.setBackground(new Color(52, 152, 219));
        btnCetak.setForeground(Color.WHITE);

        pnlButtons.add(btnPinjamBaru);
        pnlButtons.add(btnKembalikan);
        pnlButtons.add(btnHapus);
        pnlButtons.add(btnCetak);

        add(pnlButtons, BorderLayout.SOUTH);

        btnPinjamBaru.addActionListener(e -> {
            PeminjamanDialogView dialog = new PeminjamanDialogView((Frame) SwingUtilities.getWindowAncestor(this), this.transaksiController);
            dialog.setVisible(true);
            refreshTable();
        });

        btnKembalikan.addActionListener(e -> {
            int row = tableLog.getSelectedRow();
            if (row != -1) {
                int modelRow = tableLog.convertRowIndexToModel(row);
                Object idObj = tableModel.getValueAt(modelRow, 0);
                int idLog = Integer.parseInt(idObj.toString());
                transaksiController.kembalikanBuku(idLog);
                refreshTable();
            }
        });

        btnHapus.addActionListener(e -> {
            int row = tableLog.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Hapus riwayat ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int modelRow = tableLog.convertRowIndexToModel(row);
                    int idLog = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                    transaksiController.hapusLog(idLog);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            }
        });

        btnCetak.addActionListener(e -> cetakLaporan());
    }

    private void cetakLaporan() {
        try {
            MessageFormat header = new MessageFormat("Laporan Riwayat Peminjaman Perpustakaan");

            MessageFormat footer = new MessageFormat("Halaman {0,number,integer}");

            boolean complete = tableLog.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            if (complete) {
                JOptionPane.showMessageDialog(this, "Sukses Export ke PDF!");
            } else {
                JOptionPane.showMessageDialog(this, "Proses dibatalkan.");
            }

        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(this, "Gagal Mencetak: " + pe.getMessage());
        }
    }

    private void setupSelectionListener() {
        tableLog.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableLog.getSelectedRow();
                if (row != -1) {
                    String status = tableModel.getValueAt(tableLog.convertRowIndexToModel(row), 5).toString();
                    btnKembalikan.setEnabled(status.equalsIgnoreCase("Dipinjam"));
                } else {
                    btnKembalikan.setEnabled(false);
                }
            }
        });
    }
}