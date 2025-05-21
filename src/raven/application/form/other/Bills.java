package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import data.ProductData;
import models.Product;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import logic.ApiClient;
import logic.Refreshable;
import models.Order;
import models.OrderDealDetail;
import models.OrderDetail;
import raven.application.Application;
import raven.toast.Notifications;

public class Bills extends javax.swing.JPanel implements Refreshable {

    @Override
    public void refresh() {
        setupProductsTable();
    }

    private int billSeries = 1;
    private DefaultTableModel productsTableModel;
    private Map<String, List<Product>> categoryProductsMap;
    private Map<String, List<Deal>> categoryDealsMap;
    private Map<String, DefaultTableModel> billTableModels = new HashMap<>();
    private Map<String, JPanel> billPanels = new HashMap<>();
    private Map<String, JLabel> billGrandTotals = new HashMap<>();
    private ButtonGroup customerTypeGroup = new ButtonGroup();
    private boolean isDealsCategorySelected = false;

    class ProductComboBoxEditor extends DefaultCellEditor {

        private final JTable table;
        private final int column; // 3 for Size, 4 for Flavor
        private final Map<String, List<Product>> categoryProductsMap;

        public ProductComboBoxEditor(JTable table, int column, Map<String, List<Product>> categoryProductsMap) {
            super(new JComboBox<>());
            this.table = table;
            this.column = column;
            this.categoryProductsMap = categoryProductsMap;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JComboBox<String> comboBox = (JComboBox<String>) super.getTableCellEditorComponent(table, value, isSelected, row, column);
            Object valueAt = table.getValueAt(row, 0);
            int productId = (int) valueAt;
            Product product = findProductById(productId);
            if (product != null) {

//                String[] options = (this.column == 3)
//                        ? (product.sizes != null && !product.sizes.isEmpty() ? product.sizes.toArray(new String[0]) : new String[]{"N/A"})
//                        : (product.flavors != null && !product.flavors.isEmpty() ? product.flavors.toArray(new String[0]) : new String[]{"N/A"});
//                comboBox.setModel(new DefaultComboBoxModel<>(options));
                String[] options = (this.column == 3)
                        ? product.getSizes().stream().map(m -> m.name).toArray(String[]::new)
                        : product.getFlavors().stream().map(m -> m.name).toArray(String[]::new);
                comboBox.setModel(new DefaultComboBoxModel<>(options));

            } else {
                comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"-"}));
            }
            return comboBox;
        }

        private Product findProductById(int id) {
            if (id == 0) {
                return null;
            }
            for (List<Product> products : categoryProductsMap.values()) {
                if (products != null) {
                    for (Product product : products) {
                        if (product.getId() == id) {
                            return product;
                        }
                    }
                }
            }
            return null;
        }
    }

//    class Product {
//        String id;
//        String name;
//        Map<String, Integer> sizePrices;
//        List<String> sizes;
//        List<String> flavors;
//
//        public Product(String id, String name, Map<String, Integer> sizePrices, List<String> sizes, List<String> flavors) {
//            this.id = id;
//            this.name = name;
//            this.sizePrices = sizePrices;
//            this.sizes = sizes;
//            this.flavors = flavors;
//        }
//
//        public int getPriceForSize(String size) {
//            if (size == null || size.equals("N/A") || sizePrices == null || !sizePrices.containsKey(size)) {
//                if (sizes != null && !sizes.isEmpty()) {
//                    return sizePrices != null ? sizePrices.getOrDefault(sizes.get(0), 0) : 0;
//                }
//                throw new IllegalArgumentException("No valid price for size: " + size + " for product: " + name);
//            }
//            return sizePrices.get(size);
//        }
//    }
    class Deal {

        String name;
        String description;
        int price;

        public Deal(String name, String description, int price) {
            this.name = name;
            this.description = description;
            this.price = price;
        }
    }

    public Bills() {
        putClientProperty("refreshable", true);  // Mark as refreshable
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        initializeData();
        setupProductsTable();
        setupEventListeners();
    }

    private void initializeData() {
        categoryProductsMap = new HashMap<>();
        categoryDealsMap = new HashMap<>();

        // Pizzas
//        List<Product> pizzas = new ArrayList<>();
//        List<String> pizzaSizes = Arrays.asList("Small", "Medium", "Large");
//        List<String> pizzaFlavors = Arrays.asList("Pepperoni", "Fajita", "Vegetarian");
//        Map<String, Integer> pizzaPrices = new HashMap<>();
//        pizzaPrices.put("Small", 800);
//        pizzaPrices.put("Medium", 1000);
//        pizzaPrices.put("Large", 1200);
//        pizzas.add(new Product("P001", "Pizza", pizzaPrices, pizzaSizes, pizzaFlavors));
        // Drinks
//        List<Product> drinks = new ArrayList<>();
//        List<String> drinkSizes = Arrays.asList("300ML", "500ML", "1L");
//        List<String> drinkFlavors = Arrays.asList("Cola", "Lemon", "Orange");
//        Map<String, Integer> drinkPrices = new HashMap<>();
//        drinkPrices.put("300ML", 100);
//        drinkPrices.put("500ML", 150);
//        drinkPrices.put("1L", 200);
//        drinks.add(new Product("D001", "Soft Drink", drinkPrices, drinkSizes, drinkFlavors));
        // Burgers
//        List<Product> burgers = new ArrayList<>();
//        List<String> burgerSizes = Arrays.asList("Small", "Medium", "Large");
//        List<String> burgerFlavors = Arrays.asList("Zinger", "Double Zinger", "Dhamaka");
//        Map<String, Integer> burgerPrices = new HashMap<>();
//        burgerPrices.put("Small", 250);
//        burgerPrices.put("Medium", 350);
//        burgerPrices.put("Large", 450);
//        burgers.add(new Product("B001", "Burger", burgerPrices, burgerSizes, burgerFlavors));
        // Deals
//        List<Deal> deals = new ArrayList<>();
//        deals.add(new Deal("Family Deal", "2 Large Pizzas + 1L Drink", 2500));
//        deals.add(new Deal("Burger Combo", "2 Medium Burgers + 500ML Drink", 800));
//        deals.add(new Deal("Pizza Party", "3 Medium Pizzas + 2 300ML Drinks", 3200));
        // Validate that all sizes have prices
//        for (List<Product> products : new ArrayList<>(Arrays.asList(pizzas, drinks, burgers))) {
//            for (Product product : products) {
//                for (String size : product.sizes) {
//                    if (!product.sizePrices.containsKey(size)) {
//                        throw new IllegalStateException("Missing price for size " + size + " in product " + product.name);
//                    }
//                }
//            }
//        }
        categoryProductsMap = ProductData.categorizedProducts;

//        categoryProductsMap.put("Pizzas", pizzas);
//        categoryProductsMap.put("Drinks", drinks);
//        categoryProductsMap.put("Burgers", burgers);
//        categoryDealsMap.put("Deals", deals);
        Categories.setModel(new DefaultComboBoxModel<>(ProductData.stringCategories.toArray(new String[0])));
    }

    private void setupProductsTable() {
        productsTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Price", "Size", "Flavor"}
        ) {
            Class<?>[] types = new Class<?>[]{
                Integer.class, String.class, Integer.class, String.class, String.class
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Only Size and Flavor are editable
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = jTable1.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        header.setDefaultRenderer(headerRenderer);

        jTable1.setModel(productsTableModel);

        // Set custom editors for Size and Flavor columns
        jTable1.getColumnModel().getColumn(3).setCellEditor(new ProductComboBoxEditor(jTable1, 3, categoryProductsMap));
        jTable1.getColumnModel().getColumn(4).setCellEditor(new ProductComboBoxEditor(jTable1, 4, categoryProductsMap));

        // Populate table with products from all categories
        for (List<Product> products : categoryProductsMap.values()) {
            for (Product product : products) {
                String defaultSize = product.getSizes() != null && !product.getSizes().isEmpty() ? product.getSizes().get(0).name : "-";
                productsTableModel.addRow(new Object[]{
                    product.getId(),
                    product.getProductName(),
                    product.getPriceForSize(defaultSize),
                    defaultSize,
                    product.getFlavors() != null && !product.getFlavors().isEmpty() ? product.getFlavors().get(0).name : "-"
                });
            }
        }

        // Add table model listener to update price when size changes
        productsTableModel.addTableModelListener(e -> {
            if (e.getType() != TableModelEvent.UPDATE) {
                return;
            }
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (row == -1 || (column != 3 && column != 4)) {
                return;
            }

            int productId = (int) jTable1.getValueAt(row, 0);
            Object value = jTable1.getValueAt(row, column);
            System.out.println("TableModelListener: row=" + row + ", column=" + column + ", value=" + value + ", type=" + (value != null ? value.getClass().getName() : "null"));
            String newSize = null;

            if (value instanceof String) {
                newSize = (String) value;
            } else if (value instanceof Integer) {
                ProductComboBoxEditor editor = (ProductComboBoxEditor) jTable1.getColumnModel().getColumn(column).getCellEditor();
                JComboBox<String> comboBox = (JComboBox<String>) editor.getTableCellEditorComponent(jTable1, value, false, row, column);
                newSize = comboBox.getItemAt((Integer) value);
            } else if (value != null) {
                newSize = value.toString();
            }

            Product product = findProductById(productId);
            if (product == null || newSize == null) {
                System.out.println("Product or value is null for productId=" + productId);
                return;
            }
            if (column == 3) {
                int newPrice = product.getPriceForSize(newSize);
                if (newPrice > 0) {
                    productsTableModel.setValueAt(newPrice, row, 2); // Update price column
                } else {
                    JOptionPane.showMessageDialog(Bills.this,
                            "Invalid price for size: " + newSize,
                            "Price Error",
                            JOptionPane.WARNING_MESSAGE);
                    String defaultSize = product.getSizes().get(0).name;
                    productsTableModel.setValueAt(defaultSize, row, column); // Revert to default size
                    productsTableModel.setValueAt(product.getPriceForSize(defaultSize), row, 2); // Update price
                }

            }

        });
    }

    public void refreshData() {
        setupProductsTable();
    }

    // Helper method to find product by ID
    private Product findProductById(int id) {
        if (id == 0) {
            return null;
        }
        for (List<Product> products : categoryProductsMap.values()) {
            if (products != null) {
                for (Product product : products) {
                    if ((product.getId()) == (id)) {
                        return product;
                    }
                }
            }
        }
        return null;
    }

    // Helper method to find deal by name
    private Deal findDealByName(String name) {
        List<Deal> deals = categoryDealsMap.get("Deals");
        if (deals == null || name == null) {
            System.out.println("Debug: No deals found or name is null");
            return null;
        }
        name = name.trim();
        for (Deal deal : deals) {
            if (deal.name.equalsIgnoreCase(name)) {
                return deal;
            }
        }
        System.out.println("Debug: Deal not found for name: '" + name + "'");
        System.out.println("Debug: Available deal names: " + deals.stream().map(d -> d.name).collect(Collectors.joining(", ")));
        return null;
    }

    private void setupEventListeners() {
        addBill.addActionListener(e -> addNewBill());
        Categories.addActionListener(e -> {
            jTextField1.setText(""); // Clear search when category changes
            filterProductsByCategory();
        });
//        printBill.addActionListener(e -> printCurrentBill());
        removeProduct.addActionListener(e -> removeSelectedProduct());
        jButton1.addActionListener(e -> removeAllProducts());
        deleteBill.addActionListener(e -> deleteCurrentBill());

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    try {
                        addProductToBill();
                    } catch (Exception ex) {
                        System.err.println("Debug: Exception adding item: " + ex.getMessage());
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Bills.this,
                                "Error adding item: " + ex.getMessage(),
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
                        System.err.println("Debug: Exception adding item: " + ex.getMessage());
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Bills.this,
                                "Error adding item: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add search functionality
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTableBySearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTableBySearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTableBySearch();
            }
        });
    }

    private void filterTableBySearch() {
        String searchText = jTextField1.getText().trim().toLowerCase();
        String selectedCategory = (String) Categories.getSelectedItem();
        productsTableModel.setRowCount(0);

        if (isDealsCategorySelected) {
            List<Deal> deals = categoryDealsMap.get("Deals");
            if (deals != null) {
                List<Deal> filteredDeals = deals.stream()
                        .filter(deal -> deal.name.toLowerCase().contains(searchText)
                        || deal.description.toLowerCase().contains(searchText))
                        .collect(Collectors.toList());
                for (Deal deal : filteredDeals) {
                    productsTableModel.addRow(new Object[]{deal.name, deal.description});
                }
            }
        } else {
            List<Product> productsToShow = new ArrayList<>();
            if ("All".equals(selectedCategory)) {
                categoryProductsMap.values().forEach(productsToShow::addAll);
            } else {
                List<Product> products = categoryProductsMap.get(selectedCategory);
                if (products != null) {
                    productsToShow.addAll(products);
                }
            }

            List<Product> filteredProducts = productsToShow.stream()
                    .filter(product -> product.getProductName().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            for (Product product : filteredProducts) {
                String defaultSize = product.getSizes() != null && !product.getSizes().isEmpty() ? product.getSizes().get(0).name : "N/A";
                productsTableModel.addRow(new Object[]{
                    product.id,
                    product.getProductName(),
                    product.getPriceForSize(defaultSize),
                    defaultSize,
                    product.getFlavors() != null && !product.getFlavors().isEmpty() ? product.getFlavors().get(0).name : "N/A"
                });
            }
        }
    }

    private void addNewBill() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy-HHmm");
        String tabName = dateFormat.format(new Date()) + "-" + billSeries++;

        JPanel newBillPanel = new JPanel(new BorderLayout());
        JPanel billContentPanel = new JPanel();
        billContentPanel.setLayout(new BorderLayout());
        billContentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.setPreferredSize(new Dimension(800, 40));
        JLabel customerTypeLabel = new JLabel("Customer Type:");
        JRadioButton walkInRadio = new JRadioButton("Walk-in");
        JRadioButton regularRadio = new JRadioButton("Regular");
        customerTypeGroup.add(walkInRadio);
        customerTypeGroup.add(regularRadio);
        walkInRadio.setSelected(true);

        JLabel customerLabel = new JLabel("Customer ID:");
        JTextField customerField = new JTextField("WALK-IN", 20);
        customerField.setEnabled(false);

        walkInRadio.addActionListener(e -> {
            customerField.setText("WALK-IN");
            customerField.setEnabled(false);
        });

        regularRadio.addActionListener(e -> {
            customerField.setText("");
            customerField.setEnabled(true);
        });

        customerPanel.add(customerTypeLabel);
        customerPanel.add(walkInRadio);
        customerPanel.add(regularRadio);
        customerPanel.add(customerLabel);
        customerPanel.add(customerField);

        DefaultTableModel billTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Item", "Price", "Qty", "Total"}
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

        billTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        billTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        billTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        billTable.getColumnModel().getColumn(3).setPreferredWidth(60);

        billTable.setRowHeight(30);

        JScrollPane tableScrollPane = new JScrollPane(billTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 300));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel grandTotalLabel = new JLabel("Grand Total:");
        grandTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel grandTotalValue = new JLabel("0.00");
        grandTotalValue.setFont(new Font("Segoe UI", Font.BOLD, 14));
        grandTotalValue.setBorder(BorderFactory.createEtchedBorder());

        JPanel paymentPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        paymentPanel.add(new JLabel("Amount Paid:"));
        JTextField amountPaidField = new JTextField("");
        paymentPanel.add(amountPaidField);
        paymentPanel.add(new JLabel("Change Due:"));
        JLabel changeDueLabel = new JLabel("");
        changeDueLabel.setBorder(BorderFactory.createEtchedBorder());
        paymentPanel.add(changeDueLabel);

        amountPaidField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateChange();
            }

            public void removeUpdate(DocumentEvent e) {
                updateChange();
            }

            public void insertUpdate(DocumentEvent e) {
                updateChange();
            }

            private void updateChange() {
                try {
                    double paid = Double.parseDouble(amountPaidField.getText());
                    double total = Double.parseDouble(grandTotalValue.getText());
                    double change = Math.max(0, paid - total);
                    changeDueLabel.setText(String.format("%.2f", change));
                } catch (NumberFormatException ex) {
                    changeDueLabel.setText("");
                }
            }
        });

        JPanel topContentPanel = new JPanel();
        topContentPanel.setLayout(new BoxLayout(topContentPanel, BoxLayout.Y_AXIS));
        topContentPanel.add(customerPanel);
        topContentPanel.add(Box.createVerticalStrut(5));
        topContentPanel.add(tableScrollPane);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalPanel.add(grandTotalLabel);
        totalPanel.add(grandTotalValue);
        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(paymentPanel, BorderLayout.CENTER);

        billContentPanel.add(topContentPanel, BorderLayout.CENTER);
        billContentPanel.add(bottomPanel, BorderLayout.SOUTH);

        newBillPanel.add(billContentPanel, BorderLayout.CENTER);

        billTableModels.put(tabName, billTableModel);
        billPanels.put(tabName, newBillPanel);
        billGrandTotals.put(tabName, grandTotalValue);

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

            if (jTabbedPane1.getTabCount() == 0) {
                addNewBill();
            }
        }
    }

    private void filterProductsByCategory() {
        String selectedCategory = (String) Categories.getSelectedItem();
        productsTableModel.setRowCount(0);

        if ("Deals".equals(selectedCategory)) {
            isDealsCategorySelected = true;
            productsTableModel.setColumnIdentifiers(new String[]{"Deal Name", "Description"});
            List<Deal> deals = categoryDealsMap.get("Deals");
            if (deals != null) {
                for (Deal deal : deals) {
                    productsTableModel.addRow(new Object[]{deal.name, deal.description});
                }
            }
        } else {
            isDealsCategorySelected = false;
            productsTableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Price", "Size", "Flavor"});
            if ("All".equals(selectedCategory)) {
                for (List<Product> products : categoryProductsMap.values()) {
                    for (Product product : products) {
                        String defaultSize = product.getSizes() != null && !product.getSizes().isEmpty() ? product.getSizes().get(0).name : "N/A";
                        productsTableModel.addRow(new Object[]{
                            product.id,
                            product.getProductName(),
                            product.getPriceForSize(defaultSize),
                            defaultSize,
                            product.getFlavors() != null && !product.getFlavors().isEmpty() ? product.getFlavors().get(0).name : "N/A"
                        });
                    }
                }
            } else {
                List<Product> products = categoryProductsMap.get(selectedCategory);
                if (products != null) {
                    for (Product product : products) {
                        String defaultSize = product.getSizes() != null && !product.getSizes().isEmpty() ? product.getSizes().get(0).name : "N/A";
                        productsTableModel.addRow(new Object[]{
                            product.id,
                            product.getProductName(),
                            product.getPriceForSize(defaultSize),
                            defaultSize,
                            product.getFlavors() != null && !product.getFlavors().isEmpty() ? product.getFlavors().get(0).name : "N/A"
                        });
                    }
                }
            }
        }
        filterTableBySearch(); // Reapply search filter after category change
    }

    private void addProductToBill() {
        if (jTabbedPane1.getTabCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please create a bill first before adding items",
                    "No Bill Available",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a product or deal first",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        DefaultTableModel billModel = billTableModels.get(currentTab);

        try {
            if (isDealsCategorySelected) {
                Object dealNameObj = jTable1.getValueAt(selectedRow, 0);
                String dealName = (dealNameObj instanceof String) ? (String) dealNameObj : null;
                if (dealName == null || dealName.trim().isEmpty()) {
                    throw new IllegalStateException("Invalid deal name selected");
                }
                Deal deal = findDealByName(dealName);
                if (deal == null) {
                    throw new IllegalStateException("Deal not found for name: '" + dealName + "'");
                }
                int price = deal.price;
                String displayName = deal.name;

                for (int i = 0; i < billModel.getRowCount(); i++) {
                    String existingItem = (String) billModel.getValueAt(i, 0);
                    if (existingItem.equals(displayName)) {
                        int quantity = (Integer) billModel.getValueAt(i, 2);
                        billModel.setValueAt(quantity + 1, i, 2);
                        updateBillTotals(currentTab);
                        return;
                    }
                }

                billModel.addRow(new Object[]{displayName, price, 1, price});
                updateBillTotals(currentTab);
            } else {
                Object productIdObj = jTable1.getValueAt(selectedRow, 0);
                Object productNameObj = jTable1.getValueAt(selectedRow, 1);
                Object priceObj = jTable1.getValueAt(selectedRow, 2);
                Object sizeObj = jTable1.getValueAt(selectedRow, 3);
                Object flavorObj = jTable1.getValueAt(selectedRow, 4);

                int productId = (productIdObj instanceof Integer) ? (int) productIdObj : null;
                String productName = (productNameObj instanceof String) ? (String) productNameObj : null;
                Integer price = (priceObj instanceof Integer) ? (Integer) priceObj : null;
                String size = (sizeObj instanceof String) ? (String) sizeObj : null;
                String flavor = (flavorObj instanceof String) ? (String) flavorObj : null;

                if (productName == null || price == null) {
                    throw new IllegalStateException("Invalid product data selected");
                }

                if (size == null || size.isEmpty() || size.equals("N/A")) {
                    JOptionPane.showMessageDialog(this,
                            "Please select a valid size for the product",
                            "Size Required",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (flavor == null || flavor.isEmpty() || flavor.equals("N/A")) {
                    JOptionPane.showMessageDialog(this,
                            "Please select a valid flavor for the product",
                            "Flavor Required",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String displayName = String.format("%s (%s, %s)", productName, size, flavor);

                for (int i = 0; i < billModel.getRowCount(); i++) {
                    String existingItem = (String) billModel.getValueAt(i, 0);
                    if (existingItem.equals(displayName)) {
                        int quantity = (Integer) billModel.getValueAt(i, 2);
                        billModel.setValueAt(quantity + 1, i, 2);
                        updateBillTotals(currentTab);
                        return;
                    }
                }

                billModel.addRow(new Object[]{displayName, price, 1, price});
                updateBillTotals(currentTab);
            }
        } catch (Exception e) {
            System.err.println("Debug: Exception adding item: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error adding item: " + e.getMessage(),
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
        SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormatDate.format(new Date());
        String time = dateFormatTime.format(new Date());
        String orderNumber = currentTab;

        if (model == null || model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No items in the bill to print",
                    "Empty Bill",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            JPanel currentPanel = billPanels.get(currentTab);
            JPanel contentPanel = (JPanel) currentPanel.getComponent(0);
            JLabel grandTotalLabel = billGrandTotals.get(currentTab);

            // Debug: Print component hierarchy
            System.out.println("=== COMPONENT TREE ===");
            printComponentTree(contentPanel, 0);

            // Find components using improved search
            JTextField customerField = findComponent(JTextField.class, contentPanel);
            JRadioButton walkInRadio = findRadioButton("Walk-in", contentPanel);
            JRadioButton regularRadio = findRadioButton("Regular", contentPanel);

            // Debug: Print found components
            System.out.println("\n=== FOUND COMPONENTS ===");
            System.out.println("Customer Field: " + (customerField != null
                    ? "'" + customerField.getText() + "'" : "Not Found"));
            System.out.println("Walk-in Radio: " + (walkInRadio != null
                    ? (walkInRadio.isSelected() ? "Selected" : "Not Selected") : "Not Found"));
            System.out.println("Regular Radio: " + (regularRadio != null
                    ? (regularRadio.isSelected() ? "Selected" : "Not Selected") : "Not Found"));

            // Validate customer components
            String customerId = "Walk-in";
            if (regularRadio != null && regularRadio.isSelected()) {
                if (customerField == null) {
                    JOptionPane.showMessageDialog(this,
                            "System Error: Customer ID field not found!",
                            "Configuration Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String enteredId = customerField.getText().trim();
                if (enteredId.isEmpty()) {
                    customerField.requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "Please enter Customer ID for Regular customer",
                            "Input Required",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                customerId = enteredId;
            } else if (walkInRadio != null && walkInRadio.isSelected()) {
                customerId = "Walk-in";
            }

            // Build bill content
            StringBuilder billContent = new StringBuilder();
            billContent.append("      Rayyan Butt Karahi\n");
            billContent.append("  Sunrise Avenue\n");
            billContent.append("  Islamabad\n");
            billContent.append("  Tel: 051-35058062\n");
            billContent.append("================================\n");
            billContent.append("          INVOICE\n");
            billContent.append("================================\n");
            billContent.append(String.format("%-12s: %s\n", "Bill No", currentTab));
            billContent.append(String.format("%-12s: %s\n", "Date", date));
            billContent.append(String.format("%-12s: %s\n", "Time", time));
            billContent.append(String.format("%-12s: %s\n", "Customer ID", customerId));
            billContent.append("--------------------------------\n");
            billContent.append(String.format("%-16s %5s %3s %6s\n", "ITEM", "PRICE", "QTY", "TOTAL"));
            billContent.append("--------------------------------\n");

            List<OrderDetail> orderDetails = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                String item = (String) model.getValueAt(i, 0);
                int price = (Integer) model.getValueAt(i, 1);
                int qty = (Integer) model.getValueAt(i, 2);
                int total = (Integer) model.getValueAt(i, 3);
                billContent.append(String.format("%-20s %5d %3d %6d\n", item, price, qty, total));
                orderDetails.add(new OrderDetail(item, qty, total));
            }

            billContent.append("--------------------------------\n");
            String subtotalText = grandTotalLabel != null ? grandTotalLabel.getText() : "0";
            billContent.append(String.format("%-24s %6s\n", "SUBTOTAL", subtotalText));

            double subtotal;
            try {
                subtotal = Double.parseDouble(subtotalText.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException e) {
                subtotal = 0.0;
            }
            double tax = subtotal * 0.10;
            double grandTotal = subtotal + tax;

            billContent.append(String.format("%-24s %6.2f\n", "TAX (10%)", tax));
            billContent.append(String.format("%-24s %6.2f\n", "GRAND TOTAL", grandTotal));
            billContent.append("================================\n");
            billContent.append("  Thank you for your purchase!\n");
            billContent.append("  Please come again!\n");
            billContent.append("================================\n");

            // Create and send order
            List<OrderDealDetail> orderDealDetails = null;
            Order order = new Order(
                    orderNumber,
                    customerId,
                    time,
                    date,
                    grandTotal,
                    tax,
                    subtotal,
                    orderDetails,
                    orderDealDetails
            );

            String result = ApiClient.placeOrder(order);
            System.out.println("API Response: " + result);

            if (result == null || result.toLowerCase().contains("error")) {
                JOptionPane.showMessageDialog(this,
                        "Order submission failed: " + result,
                        "Server Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Please logout and login again to apply changes");

            // Print handling
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PageFormat pageFormat = printerJob.defaultPage();

            Paper paper = new Paper();
            double paperWidth = 3.5 * 72; // 3.5 inches in points
            double paperHeight = 11 * 72; // 11 inches in points
            paper.setSize(paperWidth, paperHeight);
            paper.setImageableArea(
                    18, // 0.25 inch left margin
                    18, // 0.25 inch top margin
                    paperWidth - 36, // 0.5 inch total horizontal margin
                    paperHeight - 36 // 0.5 inch total vertical margin
            );
            pageFormat.setPaper(paper);
            pageFormat.setOrientation(PageFormat.PORTRAIT);

            printerJob.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    Font font = new Font(Font.MONOSPACED, Font.PLAIN, 10);
                    g2d.setFont(font);

                    String[] lines = billContent.toString().split("\n");
                    int yPos = 15;

                    for (String line : lines) {
                        g2d.drawString(line, 10, yPos);
                        yPos += 12;
                    }
                    return Printable.PAGE_EXISTS;
                }
            }, pageFormat);

            if (printerJob.printDialog()) {
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Print failed: " + ex.getMessage(),
                            "Print Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Unexpected error: " + e.getMessage(),
                    "System Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Helper methods
    private <T extends Component> T findComponent(Class<T> componentType, Container container) {
        for (Component comp : container.getComponents()) {
            if (componentType.isInstance(comp)) {
                return componentType.cast(comp);
            }
            if (comp instanceof Container) {
                T found = findComponent(componentType, (Container) comp);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private JRadioButton findRadioButton(String buttonText, Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton radio = (JRadioButton) comp;
                if (buttonText.equals(radio.getText())) {
                    return radio;
                }
            }
            if (comp instanceof Container) {
                JRadioButton found = findRadioButton(buttonText, (Container) comp);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private void printComponentTree(Component component, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }

        String componentInfo = String.format("%s%s [%s]",
                indent.toString(),
                component.getClass().getSimpleName(),
                getComponentState(component)
        );

        System.out.println(componentInfo);

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                printComponentTree(child, depth + 1);
            }
        }
    }

    private String getComponentState(Component comp) {
        if (comp instanceof JTextField) {
            return "Text: '" + ((JTextField) comp).getText() + "'";
        }
        if (comp instanceof JRadioButton) {
            JRadioButton radio = (JRadioButton) comp;
            return "Text: '" + radio.getText() + "', Selected: " + radio.isSelected();
        }
        if (comp instanceof JLabel) {
            return "Text: '" + ((JLabel) comp).getText() + "'";
        }
        return "";
    }

    private void removeSelectedProduct() {
        String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        DefaultTableModel model = billTableModels.get(currentTab);

        if (model == null || model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No items in the bill to remove", "Empty Bill", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            JPanel billPanel = billPanels.get(currentTab);
            if (billPanel == null) {
                throw new RuntimeException("Bill panel not found for tab: " + currentTab);
            }

            // Debug: Print billPanel's layout and components
            System.out.println("billPanel layout: " + billPanel.getLayout());
            System.out.println("billPanel components: " + Arrays.toString(billPanel.getComponents()));

            // billPanel contains billContentPanel
            JPanel billContentPanel = (JPanel) billPanel.getComponent(0);
            if (billContentPanel == null) {
                throw new RuntimeException("Bill content panel not found");
            }

            // Debug: Print billContentPanel's layout and components
            System.out.println("billContentPanel layout: " + billContentPanel.getLayout());
            System.out.println("billContentPanel components: " + Arrays.toString(billContentPanel.getComponents()));

            // Find topContentPanel by searching for a JPanel that contains the JScrollPane
            JPanel topContentPanel = null;
            for (Component comp : billContentPanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component subComp : panel.getComponents()) {
                        if (subComp instanceof JScrollPane) {
                            topContentPanel = panel;
                            break;
                        }
                    }
                    if (topContentPanel != null) {
                        break;
                    }
                }
            }

            if (topContentPanel == null) {
                throw new RuntimeException("Top content panel not found");
            }

            // Debug: Print topContentPanel's layout and components
            System.out.println("topContentPanel layout: " + topContentPanel.getLayout());
            System.out.println("topContentPanel components: " + Arrays.toString(topContentPanel.getComponents()));

            // Find JScrollPane in topContentPanel
            JScrollPane scrollPane = null;
            for (Component comp : topContentPanel.getComponents()) {
                if (comp instanceof JScrollPane) {
                    scrollPane = (JScrollPane) comp;
                    break;
                }
            }

            if (scrollPane == null) {
                throw new RuntimeException("Could not find the bill table scroll pane");
            }

            JTable billTable = (JTable) scrollPane.getViewport().getView();
            int selectedRow = billTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an item to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            model.removeRow(selectedRow);
            updateBillTotals(currentTab);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void removeAllProducts() {
        String currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        DefaultTableModel model = billTableModels.get(currentTab);

        if (model.getRowCount() == 0) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove all items from the bill?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            model.setRowCount(0);
            updateBillTotals(currentTab);
        }
    }

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
    private void initComponents() {
        lb = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        Categories = new javax.swing.JComboBox<>();
        lbcat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
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

        Categories.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        lbcat.setText("Categories:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "ID", "Name", "Price"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Search Items:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(lbcat, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Categories, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(Categories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel1))
                                        .addComponent(lbcat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
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

        removeProduct.setText("Remove Item");

        jButton1.setText("Remove All Items");

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
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
    }

    private void deleteBillActionPerformed(java.awt.event.ActionEvent evt) {
        deleteCurrentBill();
    }

    private void printBillActionPerformed(java.awt.event.ActionEvent evt) {
        printCurrentBill();
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        filterTableBySearch();
    }

    private javax.swing.JComboBox<String> Categories;
    private javax.swing.JButton addBill;
    private javax.swing.JButton deleteBill;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbcat;
    private javax.swing.JButton printBill;
    private javax.swing.JButton removeProduct;
}
