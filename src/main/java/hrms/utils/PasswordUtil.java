package hrms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(plainPassword.getBytes());

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

            return saltBase64 + ":" + hashBase64;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null) {
            return false;
        }

        try {
            String[] parts = storedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            String storedHash = parts[1];

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(plainPassword.getBytes());
            String inputHash = Base64.getEncoder().encodeToString(hashedPassword);

            return storedHash.equals(inputHash);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }

    public static boolean isHashed(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String[] parts = password.split(":");
        return parts.length == 2 && parts[0].length() > 20 && parts[1].length() > 40;
    }
}
