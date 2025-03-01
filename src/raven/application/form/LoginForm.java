package raven.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import logic.AuthService;
import net.miginfocom.swing.MigLayout;
import raven.application.Application;

/**
 *
 * @author Raven
 */
public class LoginForm extends javax.swing.JPanel {

    ImageIcon loadingIcon = new ImageIcon(getClass().getResource("Loader.gif"));
    private JLabel lbError;
    private JLabel lbLoading;
    
    public LoginForm() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("al center center"));

        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");

        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "User Name");
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");

        // Enhanced Error Message Label
        lbError = new JLabel();
        lbError.setForeground(new Color(255, 50, 50)); // Modern red
        lbError.setFont(new Font("Arial", Font.BOLD, 12));
        lbError.setIcon(new ImageIcon(getClass().getResource("warning.png"))); // Add an icon
        lbError.setVisible(false);

        // Initialize the loading animation label
        lbLoading = new JLabel(loadingIcon); // Set the animated GIF
        lbLoading.setVisible(false); // Initially hidden

        panelLogin1.add(lbError, "gapy 5, wrap"); // Adds error message in layout
        panelLogin1.add(lbLoading, "gapy 5, wrap"); // Adds loading animation in layout
    }

    private void showError(String message) {
        lbError.setText("<html><span style='color:red; font-weight:bold;'>" + message + "</span></html>");
        lbError.setVisible(true);

        // Add input listeners to clear error on new input
        addInputListeners();

        // Smooth fade-in effect
        new Timer(10, e -> lbError.setForeground(new Color(255, 0, 0, Math.min(255, lbError.getForeground().getAlpha() + 25)))).start();
    }

    private void addInputListeners() {
        javax.swing.event.DocumentListener dl;
        dl = new javax.swing.event.DocumentListener() {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                clearError();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                clearError();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                clearError();
            }

            private void clearError() {
                lbError.setVisible(false);
            }
        };

        txtUser.getDocument().addDocumentListener(dl);
        txtPass.getDocument().addDocumentListener(dl);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin1 = new raven.application.form.PanelLogin();
        lbTitle = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        cmdLogin = new javax.swing.JButton();

        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Login");
        panelLogin1.add(lbTitle);

        lbUser.setText("User Name");
        panelLogin1.add(lbUser);
        panelLogin1.add(txtUser);

        lbPass.setText("Password");
        panelLogin1.add(lbPass);
        panelLogin1.add(txtPass);

        cmdLogin.setText("Login");
        cmdLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoginActionPerformed(evt);
            }
        });
        panelLogin1.add(cmdLogin);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoginActionPerformed
        // Disable the login button to prevent multiple clicks
        cmdLogin.setEnabled(false);

        // Show the loading animation
        lbLoading.setVisible(true);

        // Get the username and password
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        // Use a SwingWorker to perform the login in the background
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // Simulate a delay for the loading animation (remove this in production)
                Thread.sleep(2000);

                // Perform the authentication
                return AuthService.authenticate(username, password);
            }

            @Override
            protected void done() {
                try {
                    boolean authenticated = get(); // Get the result of the authentication

                    if (authenticated) {
                        lbError.setVisible(false);
                        Application.login();
                    } else {
                        lbError.setVisible(true);
                        showError("Invalid username or password");
                        txtPass.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("An error occurred during login");
                } finally {
                    // Hide the loading animation and re-enable the login button
                    lbLoading.setVisible(false);
                    cmdLogin.setEnabled(true);
                }
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }//GEN-LAST:event_cmdLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdLogin;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private raven.application.form.PanelLogin panelLogin1;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
