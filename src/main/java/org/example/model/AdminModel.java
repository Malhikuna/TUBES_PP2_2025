package org.example.model;

import org.example.KoneksiDB;
import java.sql.*;

public class AdminModel {

    public boolean checkLogin(String username, String password) {
        try (Connection conn = KoneksiDB.configDB()) {
            String sql = "SELECT 1 FROM admin WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
