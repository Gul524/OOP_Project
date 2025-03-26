package logic;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    // Add a user with username, password, and role
    public static boolean addUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = AuthService.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password)); // Ideally, hash the password before storing
            stmt.setString(3, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Delete a user by ID
    public static boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = AuthService.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update user details (password and role)
    public static boolean updateUser(int userId, String newUsername, String newPassword, String newRole) {
        String sql;
        boolean updatePassword = (newPassword != null && !newPassword.trim().isEmpty());

        try (Connection conn = AuthService.getConnection()) {
            if (updatePassword) {
                sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
            } else {
                sql = "UPDATE users SET username = ?, role = ? WHERE id = ?";
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newUsername);

                if (updatePassword) {
                    stmt.setString(2, hashPassword(newPassword));
                    stmt.setString(3, newRole);
                    stmt.setInt(4, userId);
                } else {
                    stmt.setString(2, newRole);
                    stmt.setInt(3, userId);
                }

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all users from the database
    public static List<Object[]> getAllUsers() {
        List<Object[]> users = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users";

        try (Connection conn = AuthService.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new Object[]{rs.getInt("id"), rs.getString("username"), rs.getString("role")});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Populate JTable with users
    public static void loadUsers(DefaultTableModel model) {
        model.setRowCount(0); // Clear table

        for (Object[] user : getAllUsers()) {
            model.addRow(new Object[]{user[0], user[1], user[2], "Edit", "Delete"});
        }
    }

    // Utility: Hash password using SHA-256
    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // Use same hashing method for consistency
    }
}
