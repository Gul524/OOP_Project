package logic;

import java.awt.Component;
import javax.swing.*;
import java.awt.*;

public class AuthService {

    private static final String VALID_USERNAME = "Anas";
    private static final String VALID_PASSWORD = "password";

    public static boolean authenticate(String username, String password) {
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }

    public static void showErrorMessage(Component parent) {
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 12));
        UIManager.put("OptionPane.background", new Color(45, 45, 45));
        UIManager.put("Panel.background", new Color(45, 45, 45));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        JOptionPane.showMessageDialog(
            parent,
            "<html><div style='text-align: center; font-size: 14px;'>❌ <b>Invalid Username or Password</b></div></html>",
            "Login Failed",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
