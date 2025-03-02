package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JOptionPane;
import logic.AuthService;
import logic.changePassword;
import raven.toast.Notifications;
import raven.application.form.LoginForm;

/**
 *
 * @author Raven
 */
public class FormUserSettings extends javax.swing.JPanel {
    LoginForm login = new LoginForm();

    public FormUserSettings() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelLogin1 = new raven.application.form.PanelLogin();
        lbTitle = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        lbPass = new javax.swing.JLabel();
        newPswd = new javax.swing.JPasswordField();
        cmdChngPswd = new javax.swing.JButton();
        currentPswd = new javax.swing.JPasswordField();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("User Settings");

        panelLogin1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Change Password");
        panelLogin1.add(lbTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 252, -1));

        lbUser.setText("Current Password");
        panelLogin1.add(lbUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 66, 252, -1));

        lbPass.setText("New Password");
        panelLogin1.add(lbPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 126, 252, -1));
        panelLogin1.add(newPswd, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 149, 252, -1));

        cmdChngPswd.setText("Change Password");
        cmdChngPswd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdChngPswdActionPerformed(evt);
            }
        });
        panelLogin1.add(cmdChngPswd, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 201, 252, -1));

        currentPswd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentPswdActionPerformed(evt);
            }
        });
        panelLogin1.add(currentPswd, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 252, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void cmdChngPswdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdChngPswdActionPerformed

        String username = AuthService.getLoggedInUser(); // Change this to dynamically fetch the logged-in user
        System.out.println(username);
        String oldPassword = new String(currentPswd.getPassword()); // Get current password from input
        String newPassword = new String(newPswd.getPassword()); // Get new password from input

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        if (newPassword.length() < 6) {
            showError("New password must be at least 6 characters long.");
            return;
        }

        if (!AuthService.validateOldPassword(username, oldPassword)) {
            showError("Old password is incorrect.");
            return;
        }

        if (AuthService.changePassword(username, newPassword)) {
            showSuccess("Password updated successfully.");
        } else {
            showError("Password update failed.");
        }

    }//GEN-LAST:event_cmdChngPswdActionPerformed

    private void currentPswdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentPswdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_currentPswdActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdChngPswd;
    private javax.swing.JPasswordField currentPswd;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private javax.swing.JPasswordField newPswd;
    private raven.application.form.PanelLogin panelLogin1;
    // End of variables declaration//GEN-END:variables
}
