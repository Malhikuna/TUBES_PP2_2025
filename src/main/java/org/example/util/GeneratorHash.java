package org.example.util;
import org.mindrot.jbcrypt.BCrypt;

public class GeneratorHash {
    public static void main(String[] args) {
        String passwordAsli = "admin123";

        String passwordHash = BCrypt.hashpw(passwordAsli, BCrypt.gensalt(12));

        System.out.println("Password Asli: " + passwordAsli);
        System.out.println("Password Hash: " + passwordHash);

        // Copy output Hash ini, lalu masukkan ke Database secara manual
        // Contoh Output: $2a$12$R9h/cIPz0gi.URNNX3kh2OPST9/PgBkqquii.V3...
    }
}
