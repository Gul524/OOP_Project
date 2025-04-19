package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Raven
 */
public class Bills extends javax.swing.JPanel {

    private int billSeries = 1;
    private DefaultTableModel productsTableModel;
    private Map<String, List<Product>> categoryProductsMap;
    private Map<String, DefaultTableModel> billTableModels = new HashMap<>();
    private Map<String, JPanel> billPanels = new HashMap<>();
    private Map<String, JLabel> billGrandTotals = new HashMap<>();
    private ButtonGroup customerTypeGroup = new ButtonGroup();

    class Product {

        String id;
        String name;
        int price;

        public Product(String id, String name, int price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    public Bills() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        initializeData();
        setupProductsTable();
        setupEventListeners();
        amountPaid.setDocument(new NumericDocument());
    }

    private void initializeData() {
        categoryProductsMap = new HashMap<>();

        List<Product> electronics = new ArrayList<>();
        electronics.add(new Product("E001", "Laptop", 1200));
        electronics.add(new Product("E002", "Smartphone", 800));
        electronics.add(new Product("E003", "Headphones", 150));

        List<Product> clothing = new ArrayList<>();
        clothing.add(new Product("C001", "T-Shirt", 25));
        clothing.add(new Product("C002", "Jeans", 50));
        clothing.add(new Product("C003", "Jacket", 80));

        List<Product> grocery = new ArrayList<>();
        grocery.add(new Product("G001", "Milk", 3));
        grocery.add(new Product("G002", "Bread", 2));
        grocery.add(new Product("G003", "Eggs", 4));

        categoryProductsMap.put("Electronics", electronics);
        categoryProductsMap.put("Clothing", clothing);
        categoryProductsMap.put("Grocery", grocery);

        Categories.setModel(new DefaultComboBoxModel<>(new String[]{"All", "Electronics", "Clothing", "Grocery"}));
    }

    private void setupProductsTable() {
        productsTableModel = (DefaultTableModel) jTable1.getModel();
        productsTableModel.setRowCount(0);

        for (List<Product> products : categoryProductsMap.values()) {
            for (Product product : products) {
                productsTableModel.addRow(new Object[]{product.id, product.name, product.price});
            }
        }
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupEventListeners() {
        addBill.addActionListener(e -> addNewBill());
        Categories.addActionListener(e -> filterProductsByCategory());
        printBill.addActionListener(e -> printCurrentBill());
        removeProduct.addActionListener(e -> removeSelectedProduct());
        jButton1.addActionListener(e -> removeAllProducts());
        deleteBill.addActionListener(e -> deleteCurrentBill());

        amountPaid.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateReturnAmount();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateReturnAmount();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateReturnAmount();
            }
        });

        // Update the mouse listener to handle errors
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    try {
                        addProductToBill();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Bills.this,
                                "Failed to add product: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    try {
                        addProductToBill();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Bills.this,
                                "Failed to add product: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void addNewBill() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy-HHmm");
        String tabName = dateFormat.format(new Date()) + "-" + billSeries++;

        JPanel newBillPanel = new JPanel();
        newBillPanel.setLayout(new java.awt.BorderLayout());

        JPanel billContentPanel = new JPanel();
        JLabel customerTypeLabel = new JLabel("Customer Type:");
        JRadioButton walkInRadio = new JRadioButton("Walk-in");
        JRadioButton regularRadio = new JRadioButton("Regular");
        customerTypeGroup.add(walkInRadio);
        customerTypeGroup.add(regularRadio);
        walkInRadio.setSelected(true);

        JLabel customerLabel = new JLabel("Customer ID:");
        JTextField customerField = new JTextField("WALK-IN");
        customerField.setEnabled(false);

        walkInRadio.addActionListener(e -> {
            customerField.setText("WALK-IN");
            customerField.setEnabled(false);
        });

        regularRadio.addActionListener(e -> {
            customerField.setText("");
            customerField.setEnabled(true);
        });

        JLabel grandTotalLabel = new JLabel("Grand Total:");
        grandTotalLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 18));
        JLabel grandTotalValue = new JLabel("00");
        grandTotalValue.setFont(new java.awt.Font("Segoe UI Black", 1, 18));
        grandTotalValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        DefaultTableModel billTableModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"Name", "Price", "Quantity", "Total"}
        ) {
            Class[] types = new Class[]{String.class, Integer.class, Integer.class, Integer.class};
            boolean[] canEdit = new boolean[]{false, false, true, false};

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        JTable billTable = new JTable(billTableModel);
        billTable.getTableHeader().setReorderingAllowed(false);
        billTableModel.addTableModelListener(e -> {
            if (e.getColumn() == 2) {
                updateBillTotals(tabName);
            }
        });

        JScrollPane scrollPane = new JScrollPane(billTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(billContentPanel);
        billContentPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(customerTypeLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(walkInRadio)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(regularRadio))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(customerLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(customerField, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(grandTotalLabel)
                                                .addGap(18, 18, 18)
                                                .addComponent(grandTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(customerTypeLabel)
                                        .addComponent(walkInRadio)
                                        .addComponent(regularRadio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(customerLabel)
                                        .addComponent(customerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(grandTotalLabel)
                                        .addComponent(grandTotalValue))
                                .addContainerGap(49, Short.MAX_VALUE))
        );

        newBillPanel.add(billContentPanel, java.awt.BorderLayout.CENTER);
        billTableModels.put(tabName, billTableModel);
        billPanels.put(tabName, newBillPanel);
        billGrandTotals.put(tabName, grandTotalValue);
        jTabbedPane1.addTab(tabName, newBillPanel);
        jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount() - 1);
        updateReturnAmount();
    }

    private void deleteCurrentBill() {
        int selectedIndex = jTabbedPane1.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "No bill selected to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete current bill?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String tabName = jTabbedPane1.getTitleAt(selectedIndex);
            billTableModels.remove(tabName);
            billPanels.remove(tabName);
            billGrandTotals.remove(tabName);
            jTabbedPane1.remove(selectedIndex);

            if (jTabbedPane1.getTabCount() == 0) {
                addNewBill();
            }
        }
    }

    private void filterProductsByCategory() {
        String selectedCategory = (String) Categories.getSelectedItem();
        productsTableModel.setRowCount(0);

        if ("All".equals(selectedCategory)) {
            for (List<Product> products : categoryProductsMap.values()) {
                for (Product product : products) {
                    productsTableModel.addRow(new Object[]{product.id, product.name, product.price});
                }
            }
        } else {
            List<Product> products = categoryProductsMap.get(selectedCategory);
            if (products != null) {
                for (Product product : products) {
                    productsTableModel.addRow(new Object[]{product.id, product.name, product.price});
                }
            }
        }
    }

    private void addProductToBill() {
        // First check if any bill exists
        if (jTabbedPane1.getTabCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please create a bill first before adding products",
                    "No Bill Available",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a product first",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        DefaultTableModel billModel = billTableModels.get(currentTab);

        try {
            String productName = (String) jTable1.getValueAt(selectedRow, 1);
            int price = (Integer) jTable1.getValueAt(selectedRow, 2);

            // Check if product exists in bill
            for (int i = 0; i < billModel.getRowCount(); i++) {
                if (productName.equals(billModel.getValueAt(i, 0))) {
                    int quantity = (Integer) billModel.getValueAt(i, 2);
                    billModel.setValueAt(quantity + 1, i, 2);
                    updateBillTotals(currentTab);
                    return;
                }
            }

            // Add new product
            billModel.addRow(new Object[]{productName, price, 1, price});
            updateBillTotals(currentTab);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error adding product: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBillTotals(String tabName) {
        DefaultTableModel model = billTableModels.get(tabName);
        JLabel grandTotalLabel = billGrandTotals.get(tabName);

        if (model == null || grandTotalLabel == null) {
            return;
        }

        int total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            int price = (Integer) model.getValueAt(i, 1);
            int quantity = (Integer) model.getValueAt(i, 2);
            int rowTotal = price * quantity;
            model.setValueAt(rowTotal, i, 3);
            total += rowTotal;
        }

        grandTotalLabel.setText(String.valueOf(total));
        updateReturnAmount();
    }

    private void printCurrentBill() {
        String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        DefaultTableModel model = billTableModels.get(currentTab);

        if (model == null || model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No items in the bill to print",
                    "Empty Bill",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Get customer info
            JPanel currentPanel = billPanels.get(currentTab);
            JPanel contentPanel = (JPanel) currentPanel.getComponent(0);
            JTextField customerField = null;
            JLabel grandTotalLabel = billGrandTotals.get(currentTab);

            // Find customer field in the panel
            for (java.awt.Component comp : contentPanel.getComponents()) {
                if (comp instanceof JTextField) {
                    customerField = (JTextField) comp;
                    break;
                }
            }

            // Create bill content
            final StringBuilder billContent = new StringBuilder();
            billContent.append("      MY STORE\n");
            billContent.append("  123 Business Street\n");
            billContent.append("  City, State 12345\n");
            billContent.append("  Tel: (123) 456-7890\n");
            billContent.append("================================\n");
            billContent.append("          INVOICE\n");
            billContent.append("================================\n");
            billContent.append(String.format("%-12s: %s\n", "Bill No", currentTab));
            billContent.append(String.format("%-12s: %s\n", "Date", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
            billContent.append(String.format("%-12s: %s\n", "Time", new SimpleDateFormat("HH:mm:ss").format(new Date())));
            billContent.append(String.format("%-12s: %s\n", "Customer",
                    customerField != null ? customerField.getText() : "N/A"));
            billContent.append("--------------------------------\n");
            billContent.append(String.format("%-16s %5s %3s %6s\n",
                    "ITEM", "PRICE", "QTY", "TOTAL"));
            billContent.append("--------------------------------\n");

            // Add items
            for (int i = 0; i < model.getRowCount(); i++) {
                String item = (String) model.getValueAt(i, 0);
                int price = (Integer) model.getValueAt(i, 1);
                int qty = (Integer) model.getValueAt(i, 2);
                int total = (Integer) model.getValueAt(i, 3);

                billContent.append(String.format("%-16.16s %5d %3d %6d\n",
                        item, price, qty, total));
            }

            billContent.append("--------------------------------\n");
            billContent.append(String.format("%-24s %6s\n", "SUBTOTAL", grandTotalLabel.getText()));

            // Calculate taxes (example: 10% tax)
            double subtotal = Double.parseDouble(grandTotalLabel.getText());
            double tax = subtotal * 0.10;
            double grandTotal = subtotal + tax;

            billContent.append(String.format("%-24s %6.2f\n", "TAX (10%)", tax));
            billContent.append(String.format("%-24s %6.2f\n", "GRAND TOTAL", grandTotal));
            billContent.append("================================\n");
            billContent.append("  Thank you for your purchase!\n");
            billContent.append("  Please come again!\n");
            billContent.append("================================\n");

            // Create a Printable object with fixed small page size
            Printable printable = new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                        throws PrinterException {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    // Set fixed small size (3.5 inches wide)
                    double width = 3.5 * 72; // 72 points per inch
                    double height = 11 * 72; // 11 inches tall (typical receipt paper)
                    pageFormat.setPaper(new java.awt.print.Paper());
                    java.awt.print.Paper paper = pageFormat.getPaper();
                    paper.setSize(width, height);
                    paper.setImageableArea(0.25 * 72, 0.25 * 72,
                            width - 0.5 * 72, height - 0.5 * 72);
                    pageFormat.setPaper(paper);

                    // Set font and draw the text
                    Font font = new Font("Monospaced", Font.PLAIN, 9);
                    g2d.setFont(font);

                    // Split the bill content into lines
                    String[] lines = billContent.toString().split("\n");

                    // Draw each line
                    int y = 15;
                    for (String line : lines) {
                        g2d.drawString(line, 5, y);
                        y += 12;
                    }

                    return Printable.PAGE_EXISTS;
                }
            };

            // Create a printer job with fixed page format
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PageFormat pageFormat = printerJob.defaultPage();

            // Set small page size
            java.awt.print.Paper paper = new java.awt.print.Paper();
            double width = 3.5 * 72; // 3.5 inches in points (72 points per inch)
            double height = 11 * 72; // 11 inches (typical receipt paper)
            paper.setSize(width, height);
            paper.setImageableArea(0.25 * 72, 0.25 * 72,
                    width - 0.5 * 72, height - 0.5 * 72);
            pageFormat.setPaper(paper);
            pageFormat.setOrientation(PageFormat.PORTRAIT);

            printerJob.setPrintable(printable, pageFormat);

            // Show print dialog
            if (printerJob.printDialog()) {
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Failed to print bill: " + ex.getMessage(),
                            "Print Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error generating bill: " + e.getMessage(),
                    "Print Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedProduct() {
        String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        DefaultTableModel model = billTableModels.get(currentTab);

        if (model == null || model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No products in the bill to remove", "Empty Bill", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Get the bill panel
            JPanel billPanel = billPanels.get(currentTab);

            // Find the JScrollPane in the panel's components
            JScrollPane scrollPane = null;
            for (java.awt.Component comp : billPanel.getComponents()) {
                if (comp instanceof JScrollPane) {
                    scrollPane = (JScrollPane) comp;
                    break;
                }
            }

            if (scrollPane == null) {
                // Alternative approach - get the first component of the panel (which should be the content panel)
                JPanel contentPanel = (JPanel) billPanel.getComponent(0);
                for (java.awt.Component comp : contentPanel.getComponents()) {
                    if (comp instanceof JScrollPane) {
                        scrollPane = (JScrollPane) comp;
                        break;
                    }
                }

                if (scrollPane == null) {
                    throw new RuntimeException("Could not find the bill table scroll pane");
                }
            }

            // Get the table from the scroll pane
            JTable billTable = (JTable) scrollPane.getViewport().getView();
            int selectedRow = billTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            model.removeRow(selectedRow);
            updateBillTotals(currentTab);
            updateReturnAmount();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeAllProducts() {
        String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        DefaultTableModel model = billTableModels.get(currentTab);

        if (model.getRowCount() == 0) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove all products from the bill?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            model.setRowCount(0);
            updateBillTotals(currentTab);
        }
    }

    private void updateReturnAmount() {
        try {
            String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
            if (currentTab == null) {
                return;
            }

            JLabel grandTotalLabel = billGrandTotals.get(currentTab);
            if (grandTotalLabel == null) {
                return;
            }

            // Get current grand total
            String totalText = grandTotalLabel.getText();
            int grandTotal = totalText.equals("00") ? 0 : Integer.parseInt(totalText);

            // Get paid amount (default to 0 if empty)
            String paidText = amountPaid.getText().trim();
            int paidAmount = paidText.isEmpty() ? 0 : Integer.parseInt(paidText);

            // Calculate return amount (never negative)
            int returnAmount = Math.max(paidAmount - grandTotal, 0);
            jLabel5.setText(String.valueOf(returnAmount));

        } catch (NumberFormatException e) {
            jLabel5.setText("0");
        }
    }

    // NumericDocument class to filter non-numeric input
    class NumericDocument extends javax.swing.text.PlainDocument {

        @Override
        public void insertString(int offs, String str, javax.swing.text.AttributeSet a)
                throws javax.swing.text.BadLocationException {
            if (str == null) {
                return;
            }

            String currentText = getText(0, getLength());
            String newText = currentText.substring(0, offs) + str + currentText.substring(offs);

            try {
                if (!newText.isEmpty()) {
                    Integer.parseInt(newText);
                }
                super.insertString(offs, str, a);
            } catch (NumberFormatException e) {
                // Ignore non-numeric input
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        Categories = new javax.swing.JComboBox<>();
        lbcat = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        printBill = new javax.swing.JButton();
        removeProduct = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        amountPaid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        addBill = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        deleteBill = new javax.swing.JButton();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Bills");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Categories.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbcat.setText("Categories:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbcat, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Categories, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Categories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbcat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        printBill.setText("Print Bill");
        printBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBillActionPerformed(evt);
            }
        });

        removeProduct.setText("Remove Product");

        jButton1.setText("Remove All Products");

        jLabel3.setText("Total Amount Paid:");

        amountPaid.setText("00");
        amountPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountPaidActionPerformed(evt);
            }
        });

        jLabel4.setText("Amount to be Returned:");

        jLabel5.setText("00");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(printBill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator5)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(amountPaid, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(removeProduct)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(23, 23, 23)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(printBill)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addBill.setText("Add New Bill");

        deleteBill.setText("Delete Bill");
        deleteBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(addBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteBill)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addBill)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void printBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_printBillActionPerformed

    private void deleteBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBillActionPerformed

    private void amountPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountPaidActionPerformed
        // TODO add your handling code here:
        amountPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    try {
                        updateReturnAmount();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Bills.this,
                                "Failed to add product: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }//GEN-LAST:event_amountPaidActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Categories;
    private javax.swing.JButton addBill;
    private javax.swing.JTextField amountPaid;
    private javax.swing.JButton deleteBill;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbcat;
    private javax.swing.JButton printBill;
    private javax.swing.JButton removeProduct;
    // End of variables declaration//GEN-END:variables
}
