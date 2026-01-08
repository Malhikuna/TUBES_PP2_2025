package org.example.util;

import javax.swing.table.DefaultTableModel;

public class GeneratorID {
    public static String generate(DefaultTableModel model, String prefix, int idColumnIndex) {
        int max = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            Object value = model.getValueAt(i, idColumnIndex);

            if (value == null) continue;

            String id = value.toString();

            if (!id.matches(prefix + "\\d+")) continue;

            int num = Integer.parseInt(id.substring(prefix.length()));
            if (num > max) {
                max = num;
            }
        }

        return String.format("%s%03d", prefix, max + 1);
    }

    public static String generateAnggota(DefaultTableModel model) {
        return generate(model, "A", 1);
    }

    public static String generateBuku(DefaultTableModel model) {
        return generate(model, "B", 1);
    }
}
