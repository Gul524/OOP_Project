package raven.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import raven.application.Application;
import raven.application.form.other.*;
import raven.application.form.other.Bills;
import raven.menu.Menu;
import raven.menu.MenuAction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import logic.ApiClient;

/**
 * @author Raven
 */
public class MainForm extends JLayeredPane {

    // Singleton instances of forms
    private final FormDashboard formDashboard;
    private final FormCategories formCategories;
    private final FormProducts formProducts;
    private final FormDeals formDeals;
    private final FormStaff formStaff;
    private final FormInventory formInventory;
    private final Bills formBills;

    public MainForm() {
        // Initialize singleton instances of forms
        formDashboard = new FormDashboard(); // Adjust if FormDashboard has a singleton pattern
        formCategories = new FormCategories(); // Adjust if FormCategories has a singleton pattern
        formProducts = new FormProducts(); // Use the singleton instance
        formDeals = new FormDeals(); // Adjust if FormDeals has a singleton pattern
        formStaff = new FormStaff(); // Adjust if FormStaff has a singleton pattern
        formInventory = new FormInventory(); // Adjust if FormInventory has a singleton pattern
        formBills = new Bills(); // Adjust if Bills has a singleton pattern

        init();
    }

    public void refreshAll() {
        // Refresh data from API
        ApiClient.loadCategories();
        ApiClient.loadOrders();
        ApiClient.loadProducts();
        ApiClient.loadStaff();

        // Refresh all forms
        formDashboard.refreshData();
        formCategories.refreshData();
        formProducts.refreshData();
        formDeals.refreshData();
        formStaff.refreshData();
        formInventory.refreshData();
        formBills.refreshData();

        // Refresh the currently visible form only if panelBody has components
        if (panelBody.getComponentCount() > 0) {
            Component current = panelBody.getComponent(0);
            if (current instanceof Refreshable) {
                try {
                    ((Refreshable) current).refresh();
                } catch (RefreshFailedException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Specific form refreshes (redundant with the above Refreshable check)
            if (current instanceof FormDashboard) {
                ((FormDashboard) current).refreshData();
            } else if (current instanceof FormCategories) {
                ((FormCategories) current).refreshData();
            } else if (current instanceof FormProducts) {
                ((FormProducts) current).refreshData();
            } else if (current instanceof FormDeals) {
                ((FormDeals) current).refreshData();
            } else if (current instanceof FormStaff) {
                ((FormStaff) current).refreshData();
            } else if (current instanceof FormInventory) {
                ((FormInventory) current).refreshData();
            } else if (current instanceof Bills) {
                ((Bills) current).refreshData();
            }
        }

        // Force UI update
        revalidate();
        repaint();
    }

    private void init() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());
        menu = new Menu();
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        initMenuEvent();
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(panelBody);
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("raven/icon/svg/" + icon, 0.8f));
    }

    private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            switch (index) {
                case 0 ->
                    Application.showForm(formDashboard);
                case 1 -> {
                    if (subIndex == 1) {
                        Application.showForm(formCategories);
                    }
                    if (subIndex == 2) {
                        Application.showForm(formProducts);
                    }
                    if (subIndex == 3) {
                        Application.showForm(formDeals);
                    }
                }
                case 2 ->
                    Application.showForm(formStaff);
                case 3 ->
                    Application.showForm(formInventory);
                case 4 ->
                    Application.showForm(formBills);
                case 5 ->
                    Application.logout();
                default ->
                    action.cancel();
            }
        });
    }

    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("raven/icon/svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
            }
        }
    }
}
