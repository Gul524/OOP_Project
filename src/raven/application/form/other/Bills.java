package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        List<String> sizes;
        List<String> flavors;
    
        public Product(String id, String name, int price, List<String> pizzaSizes, List<String> pizzaFlavors) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.sizes = pizzaSizes; // Correct assignment
            this.flavors = pizzaFlavors; // Correct assignment
        }
    }

    public Bills() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        initializeData();
        setupProductsTable();
        setupEventListeners();
//        amountPaid.setDocument(new NumericDocument());
    }

    private void initializeData() {
        categoryProductsMap = new HashMap<>();
    
        List<Product> pizzas = new ArrayList<>();
        List<String> pizzaSizes = Arrays.asList("Small", "Medium", "Large");
        List<String> pizzaFlavors = Arrays.asList("Pepperoni", "Fajita", "Vegetarian");
        pizzas.add(new Product("P001", "Pizza", 1200, pizzaSizes, pizzaFlavors));
    
        List<Product> drinks = new ArrayList<>();
        List<String> drinkSizes = Arrays.asList("300ML", "500ML", "1L");
        List<String> drinkFlavors = Arrays.asList("Cola", "Lemon", "Orange");
        drinks.add(new Product("D001", "Soft Drink", 150, drinkSizes, drinkFlavors));
    
        List<Product> burgers = new ArrayList<>();
        List<String> burgerSizes = Arrays.asList("Small", "Medium", "Large");
        List<String> burgerFlavors = Arrays.asList("Zinger", "Double Zinger", "Dhamaka");
        burgers.add(new Product("B001", "Burger", 350, burgerSizes, burgerFlavors));
    
        categoryProductsMap.put("Pizzas", pizzas);
        categoryProductsMap.put("Drinks", drinks);
        categoryProductsMap.put("Burgers", burgers);
    
        Categories.setModel(new DefaultComboBoxModel<>(new String[]{"All", "Pizzas", "Drinks", "Burgers"}));
    }
    
    private void setupProductsTable() {
        // Update column names
        productsTableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Name", "Price", "Size", "Flavor"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Only Size and Flavor are editable
            }
        };
    
        jTable1.setModel(productsTableModel);
    
        // Set custom editors for Size and Flavor columns
        jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JComboBox<>()));
        jTable1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox<>()));
    
        // Populate table with products from all categories
        for (List<Product> products : categoryProductsMap.values()) {
            for (Product product : products) {
                productsTableModel.addRow(new Object[]{
                    product.id,
                    product.name,
                    product.price,
                    product.sizes != null && !product.sizes.isEmpty() ? product.sizes.get(0) : "",
                    product.flavors != null && !product.flavors.isEmpty() ? product.flavors.get(0) : ""
                });
            }
        }
    
        // Add selection listener to update dropdowns when row is selected
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = jTable1.getSelectedRow();
                if (row >= 0) {
                    String productId = (String) jTable1.getValueAt(row, 0);
                    Product product = findProductById(productId);
    
                    if (product != null) {
                        // Update size dropdown
                        JComboBox<String> sizeCombo = new JComboBox<>(
                            product.sizes != null && !product.sizes.isEmpty()
                                ? product.sizes.toArray(new String[0])
                                : new String[]{"N/A"}
                        );
                        jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(sizeCombo));
    
                        // Update flavor dropdown
                        JComboBox<String> flavorCombo = new JComboBox<>(
                            product.flavors != null && !product.flavors.isEmpty()
                                ? product.flavors.toArray(new String[0])
                                : new String[]{"N/A"}
                        );
                        jTable1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(flavorCombo));
                    }
                }
            }
        });
    }
    
    
    // Helper method to find product by ID
    private Product findProductById(String id) {
        for (List<Product> products : categoryProductsMap.values()) {
            if (products != null) {
                for (Product product : products) {
                    if (product.id.equals(id)) {
                        return product;
                    }
                }
            }
        }
        return null;
    }

    private void setupEventListeners() {
        addBill.addActionListener(e -> addNewBill());
        Categories.addActionListener(e -> filterProductsByCategory());
        printBill.addActionListener(e -> printCurrentBill());
        removeProduct.addActionListener(e -> removeSelectedProduct());
        jButton1.addActionListener(e -> removeAllProducts());
        deleteBill.addActionListener(e -> deleteCurrentBill());

//        amountPaid.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
//            public void changedUpdate(javax.swing.event.DocumentEvent e) {
////                updateReturnAmount();
//            }
//
//            public void removeUpdate(javax.swing.event.DocumentEvent e) {
////                updateReturnAmount();
//            }
//
//            public void insertUpdate(javax.swing.event.DocumentEvent e) {
////                updateReturnAmount();
//            }
//        });

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
        // Generate a unique tab name with timestamp and bill series
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy-HHmm");
        String tabName = dateFormat.format(new Date()) + "-" + billSeries++;
    
        // Create the main bill panel
        JPanel newBillPanel = new JPanel(new BorderLayout());
    
        // Create content panel for bill components
        JPanel billContentPanel = new JPanel();
        billContentPanel.setLayout(new BorderLayout());
    
        // Customer type selection
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel customerTypeLabel = new JLabel("Customer Type:");
        JRadioButton walkInRadio = new JRadioButton("Walk-in");
        JRadioButton regularRadio = new JRadioButton("Regular");
        customerTypeGroup.add(walkInRadio);
        customerTypeGroup.add(regularRadio);
        walkInRadio.setSelected(true);
    
        // Customer ID field
        JLabel customerLabel = new JLabel("Customer ID:");
        JTextField customerField = new JTextField("WALK-IN", 20);
        customerField.setEnabled(false);
    
        // Add listeners for customer type changes
        walkInRadio.addActionListener(e -> {
            customerField.setText("WALK-IN");
            customerField.setEnabled(false);
        });
    
        regularRadio.addActionListener(e -> {
            customerField.setText("");
            customerField.setEnabled(true);
        });
    
        // Create the bill table with size/flavor support
        DefaultTableModel billTableModel = new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"Item (Size, Flavor)", "Price", "Qty", "Total"}
        ) {
            Class[] types = new Class[]{String.class, Integer.class, Integer.class, Integer.class};
            boolean[] canEdit = new boolean[]{false, false, true, false};
    
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
    
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    
        JTable billTable = new JTable(billTableModel);
        billTable.getTableHeader().setReorderingAllowed(false);
    
        // Set column widths
        billTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        billTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        billTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        billTable.getColumnModel().getColumn(3).setPreferredWidth(60);
    
        // Add listener for quantity changes
        billTableModel.addTableModelListener(e -> {
            if (e.getColumn() == 2) {
                updateBillTotals(tabName);
            }
        });
    
        // Grand total display
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel grandTotalLabel = new JLabel("Grand Total:");
        grandTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel grandTotalValue = new JLabel("0.00");
        grandTotalValue.setFont(new Font("Segoe UI", Font.BOLD, 14));
        grandTotalValue.setBorder(BorderFactory.createEtchedBorder());
    
        // Payment section
        JPanel paymentPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        paymentPanel.add(new JLabel("Amount Paid:"));
        JTextField amountPaidField = new JTextField("0.00");
        paymentPanel.add(amountPaidField);
        paymentPanel.add(new JLabel("Change Due:"));
        JLabel changeDueLabel = new JLabel("0.00");
        changeDueLabel.setBorder(BorderFactory.createEtchedBorder());
        paymentPanel.add(changeDueLabel);
    
        // Add document listener to calculate change
        amountPaidField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateChange(); }
            public void removeUpdate(DocumentEvent e) { updateChange(); }
            public void insertUpdate(DocumentEvent e) { updateChange(); }
            
            private void updateChange() {
                try {
                    double paid = Double.parseDouble(amountPaidField.getText());
                    double total = Double.parseDouble(grandTotalValue.getText());
                    double change = Math.max(0, paid - total);
                    changeDueLabel.setText(String.format("%.2f", change));
                } catch (NumberFormatException ex) {
                    changeDueLabel.setText("0.00");
                }
            }
        });
    
        // Assemble customer panel
        customerPanel.add(customerTypeLabel);
        customerPanel.add(walkInRadio);
        customerPanel.add(regularRadio);
        customerPanel.add(customerLabel);
        customerPanel.add(customerField);
    
        // Assemble the bill content panel
        billContentPanel.add(customerPanel, BorderLayout.NORTH);
        billContentPanel.add(new JScrollPane(billTable), BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(paymentPanel, BorderLayout.CENTER);
        
        totalPanel.add(grandTotalLabel);
        totalPanel.add(grandTotalValue);
        
        billContentPanel.add(bottomPanel, BorderLayout.SOUTH);
    
        // Add everything to the main panel
        newBillPanel.add(billContentPanel, BorderLayout.CENTER);
    
        // Store references for later use
        billTableModels.put(tabName, billTableModel);
        billPanels.put(tabName, newBillPanel);
        billGrandTotals.put(tabName, grandTotalValue);
    
        // Add the new tab
        jTabbedPane1.addTab(tabName, newBillPanel);
        jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount() - 1);
    }

    private void deleteCurrentBill() {
        int selectedIndex = jTabbedPane1.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "No bill selected to delete", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
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
    
            // If no bills left, create a new one
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
                    productsTableModel.addRow(new Object[]{
                        product.id,
                        product.name,
                        product.price,
                        product.sizes != null && !product.sizes.isEmpty() ? product.sizes.get(0) : "",
                        product.flavors != null && !product.flavors.isEmpty() ? product.flavors.get(0) : ""
                    });
                }
            }
        } else {
            List<Product> products = categoryProductsMap.get(selectedCategory);
            if (products != null) {
                for (Product product : products) {
                    productsTableModel.addRow(new Object[]{
                        product.id,
                        product.name,
                        product.price,
                        product.sizes != null && !product.sizes.isEmpty() ? product.sizes.get(0) : "",
                        product.flavors != null && !product.flavors.isEmpty() ? product.flavors.get(0) : ""
                    });
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
            String productId = (String) jTable1.getValueAt(selectedRow, 0);
            String productName = (String) jTable1.getValueAt(selectedRow, 1);
            int price = (Integer) jTable1.getValueAt(selectedRow, 2);
            String size = (String) jTable1.getValueAt(selectedRow, 3);
            String flavor = (String) jTable1.getValueAt(selectedRow, 4);

            // Validate size and flavor selection
            if (size == null || size.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please select a size for the product",
                        "Size Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (flavor == null || flavor.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please select a flavor for the product",
                        "Flavor Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Format the display name with size and flavor
            String displayName = String.format("%s (%s, %s)", productName, size, flavor);

            // Check if product with same size/flavor already exists in bill
            for (int i = 0; i < billModel.getRowCount(); i++) {
                String existingItem = (String) billModel.getValueAt(i, 0);
                if (existingItem.equals(displayName)) {
                    int quantity = (Integer) billModel.getValueAt(i, 2);
                    billModel.setValueAt(quantity + 1, i, 2);
                    updateBillTotals(currentTab);
                    return;
                }
            }

            // Add new product to bill
            billModel.addRow(new Object[]{
                displayName, // Formatted name with size/flavor
                price, // Original price
                1, // Initial quantity
                price // Initial total (price * 1)
            });

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

                billContent.append(String.format("%-20s %5d %3d %6d\n",
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(removeProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(printBill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator5)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(44, 44, 44)
                .addComponent(printBill)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBillActionPerformed

    private void printBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_printBillActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Categories;
    private javax.swing.JButton addBill;
    private javax.swing.JButton deleteBill;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbcat;
    private javax.swing.JButton printBill;
    private javax.swing.JButton removeProduct;
    // End of variables declaration//GEN-END:variables
}
