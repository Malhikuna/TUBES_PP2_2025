package org.example.view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomInputPanel extends JPanel {
    private final JTextField field;
    private final boolean isPassword;

    public CustomInputPanel(String placeholder, String type) {
        this.isPassword = type.equals("LOCK");
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        setBackground(new Color(240, 245, 250));

        // Bagian Ikon
        JPanel iconPanel = new JPanel(new GridBagLayout());
        iconPanel.setBackground(new Color(38, 111, 70));
        iconPanel.setPreferredSize(new Dimension(50, 50));

        JLabel lblIcon = new JLabel(new DrawingIcon(type));
        iconPanel.add(lblIcon);
        add(iconPanel, BorderLayout.WEST);

        // Bagian Text Field
        if (isPassword) {
            field = new JPasswordField();
            ((JPasswordField)field).setEchoChar((char) 0);
        } else {
            field = new JTextField();
        }

        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new EmptyBorder(0, 15, 0, 15));
        field.setBackground(new Color(230, 235, 245));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (isPassword) {
                        ((JPasswordField)field).setEchoChar('●');
                    }
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                    if (isPassword) {
                        ((JPasswordField)field).setEchoChar((char) 0);
                    }
                }
            }
        });

        add(field, BorderLayout.CENTER);
    }

    public String getText() {
        return isPassword ? new String(((JPasswordField) field).getPassword()) : field.getText();
    }

    public JTextField getField() { return field; }
}
