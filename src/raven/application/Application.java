package raven.application;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import logic.ApiClient;
import raven.application.form.LoginForm;
import raven.application.form.MainForm;
import raven.application.form.MainFormStaff;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author AnasJ
 */
public class Application extends javax.swing.JFrame {

    private static Application app;
    public static MainForm mainForm;
    public static MainFormStaff mainFormStaff;
    private final LoginForm loginForm;

    public Application() {
        initComponents();
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        mainForm = new MainForm();
        mainFormStaff = new MainFormStaff();
        loginForm = new LoginForm();
        setContentPane(loginForm);
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        Notifications.getInstance().setJFrame(this);
    }

    public static void showForm(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainForm.showForm(component);
    }

    public static void showFormStaff(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainFormStaff.showForm(component);
    }

    public static void login() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainForm);
        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainForm.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void loginStaff() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainFormStaff);
        app.mainFormStaff.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenuStaff(0, 0);
        app.mainFormStaff.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainFormStaff);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void logout() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginForm);
        app.loginForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.loginForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();

    }

    public static void setSelectedMenu(int index, int subIndex) {
        app.mainForm.setSelectedMenu(index, subIndex);
    }

    public static void setSelectedMenuStaff(int index, int subIndex) {
        app.mainFormStaff.setSelectedMenu(index, subIndex);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void refreshApplication() {
        if (mainForm != null) {
            mainForm.refreshAll();
        }
        if (mainFormStaff != null) {
            mainForm.refreshAll();
        }
        SwingUtilities.updateComponentTreeUI(app);
    }

    public static void main(String args[]) {
        FlatRobotoFont.install();
        try {
            ApiClient.loadCategories();
            ApiClient.loadProducts();
            ApiClient.loadStaff();
            ApiClient.loadOrders();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FlatLaf.registerCustomDefaultsSource("raven.theme");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            app = new Application();
            //  app.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            app.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
