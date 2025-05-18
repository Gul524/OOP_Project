package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import logic.ApiClient;
import models.Product;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import models.Category;
import data.ProductData;
import java.awt.Component;
import models.Flavor;
import models.Size;

/**
 * @author anas
 */
public class FormProducts extends javax.swing.JPanel {

    DefaultTableModel productTableModel = new DefaultTableModel(new Object[][] {},
            new String[] {
                    "Name", "Size", "Flavor"
            }) {
        Class[] types = new Class[] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
        };
        boolean[] canEdit = new boolean[] {
                false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };

    private boolean isEditing = false;
    private int editingProductId = -1;
    private List<Integer> productIds = new ArrayList<>(); // To store product IDs internally

    public FormProducts() {
        initComponents();
        loadProducts();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        setupSearchAndFilter();
    }

    private void setupSearchAndFilter() {
        categoriesList1.addActionListener(e -> {
            Category selectedCategory = (Category) categoriesList1.getSelectedItem();
            String categoryName = selectedCategory != null ? selectedCategory.getCategoryName() : "All";
            filterProducts(categoryName, searchProduct.getText().trim());
        });

        searchProduct.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearch();
            }

            private void updateSearch() {
                Category selectedCategory = (Category) categoriesList1.getSelectedItem();
                String categoryName = selectedCategory != null ? selectedCategory.getCategoryName() : "All";
                filterProducts(categoryName, searchProduct.getText().trim());
            }
        });
    }

    private void filterProducts(String category, String searchText) {
        productTableModel.setRowCount(0);
        productIds.clear();
        showProducts(productTableModel, category, searchText);
    }

    void showProducts(DefaultTableModel tableModel, String category, String searchText) {
        try {
            List<Product> productsToShow;

            if ("All".equals(category)) {
                productsToShow = new ArrayList<>();
                for (List<Product> products : ProductData.categorizedProducts.values()) {
                    productsToShow.addAll(products);
                }
            } else {
                productsToShow = ProductData.categorizedProducts.get(category);
                if (productsToShow == null) {
                    return;
                }
            }

            for (Product p : productsToShow) {
                boolean matchesSearch = searchText.isEmpty()
                        || p.getProductName().toLowerCase().contains(searchText.toLowerCase())
                        || p.getSizesString().toLowerCase().contains(searchText.toLowerCase())
                        || p.getFalovorsString().toLowerCase().contains(searchText.toLowerCase());

                if (matchesSearch) {
                    tableModel.addRow(new Object[] {
                            p.getProductName(),
                            p.getSizesString(),
                            p.getFalovorsString()
                    });
                    productIds.add(p.getId()); // Store the product ID internally
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void loadProducts() {
        List<Category> categoriesWithAll = new ArrayList<>();
        categoriesWithAll.add(new Category("All"));
        categoriesWithAll.addAll(ProductData.categories);

        categoriesList.setModel(new DefaultComboBoxModel<>(ProductData.categories.toArray(new Category[0])));
        categoriesList1.setModel(new DefaultComboBoxModel<>(categoriesWithAll.toArray(new Category[0])));
        showProducts(productTableModel, "All", "");

        categoriesList.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Category) {
                    setText(((Category) value).getCategoryName());
                }
                return this;
            }
        });

        categoriesList1.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Category) {
                    setText(((Category) value).getCategoryName());
                }
                return this;
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lb = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        flavors = new javax.swing.JScrollPane();
        tblFlavors = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        Sizes = new javax.swing.JScrollPane();
        tblSizes = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        addProduct = new javax.swing.JButton();
        dltProduct = new javax.swing.JButton();
        editItem = new javax.swing.JButton();
        lblCategory = new javax.swing.JLabel();
        categoriesList = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        searchProduct = new javax.swing.JTextField();
        lblCategory1 = new javax.swing.JLabel();
        categoriesList1 = new javax.swing.JComboBox<>();

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setModel(productTableModel);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Products");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblName.setText("Name:");

        jLabel1.setText("Flavors:");

        jButton1.setText("Add Flavor");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblFlavors.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "Flavors" }) {
            Class[] types = new Class[] { java.lang.String.class };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tblFlavors.getTableHeader().setReorderingAllowed(false);
        flavors.setViewportView(tblFlavors);
        if (tblFlavors.getColumnModel().getColumnCount() > 0) {
            tblFlavors.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel2.setText("Sizes:");

        tblSizes.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "Size", "Price" }) {
            Class[] types = new Class[] { java.lang.String.class, java.lang.Integer.class };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tblSizes.getTableHeader().setReorderingAllowed(false);
        Sizes.setViewportView(tblSizes);
        if (tblSizes.getColumnModel().getColumnCount() > 0) {
            tblSizes.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton2.setText("Add Size");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        addProduct.setText("Add Product");
        addProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductActionPerformed(evt);
            }
        });

        dltProduct.setText("Delete Product");
        dltProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dltProductActionPerformed(evt);
            }
        });

        editItem.setText("Edit Product");
        editItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductActionPerformed(evt);
            }
        });

        lblCategory.setText("Category:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel3.setText("Add Product");

        lblSearch.setText("Search Product:");

        lblCategory1.setText("Category:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(flavors, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                        .addComponent(Sizes, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addGap(29, 29, 29)))
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(name)
                                                                        .addComponent(categoriesList, 0, 193, Short.MAX_VALUE))))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jButton1)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(addProduct)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dltProduct)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editItem)
                                .addGap(62, 62, 62))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jLabel3)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblName))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCategory)
                                        .addComponent(categoriesList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton2)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jButton1)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel1)
                                                                .addComponent(flavors, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(Sizes, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addProduct)
                                        .addComponent(dltProduct)
                                        .addComponent(editItem))
                                .addGap(26, 26, 26)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblCategory1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(categoriesList1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(215, 215, 215)
                                                .addComponent(lblSearch)
                                                .addGap(18, 18, 18)
                                                .addComponent(searchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb)
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCategory1)
                                        .addComponent(categoriesList1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lblSearch)
                                                .addComponent(searchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }

    public List<Flavor> getFlavorsList() {
        List<Flavor> flavorsList = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblFlavors.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            String flavorName = (String) model.getValueAt(row, 0);
            if (flavorName != null && !flavorName.trim().isEmpty()) {
                flavorsList.add(new Flavor(flavorName));
            }
        }
        return flavorsList;
    }

    public List<Size> getSizesList() {
        List<Size> sizesList = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblSizes.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            String sizeName = (String) model.getValueAt(row, 0);
            Integer price = (Integer) model.getValueAt(row, 1);
            if (sizeName != null && !sizeName.trim().isEmpty() && price != null) {
                sizesList.add(new Size(sizeName, price));
            }
        }
        return sizesList;
    }

    private void addProductActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Validate product name
            String productName = name.getText().trim();
            if (productName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product Name cannot be empty.");
                return;
            }

            // Validate category
            Category selectedCategory = (Category) categoriesList.getSelectedItem();
            if (selectedCategory == null) {
                JOptionPane.showMessageDialog(this, "Please select a category.");
                return;
            }

            // Get flavors and sizes
            List<Flavor> flavors = getFlavorsList();
            List<Size> sizes = getSizesList();
            if (sizes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "At least one size with price is required.");
                return;
            }

            // Use the first size's price as the base price
            int price = sizes.get(0).price;

            // Create Product object
            Product product = new Product(
                    selectedCategory.getId(),
                    productName,
                    price,
                    sizes,
                    flavors
            );

            String result;
            if (isEditing) {
                // Update existing product
                result = ApiClient.updateProduct(editingProductId, product);
                if (result.contains("Success")) {
                    JOptionPane.showMessageDialog(this, "Product updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update product: " + result);
                    return;
                }
            } else {
                // Add new product
                List<Product> products = new ArrayList<>();
                products.add(product);
                result = ApiClient.addProduct(products);
                if (result.contains("Success")) {
                    JOptionPane.showMessageDialog(this, "Product added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product: " + result);
                    return;
                }
            }

            // Reload products from API
            ApiClient.loadProducts();

            // Clear input fields and reset editing state
            name.setText("");
            ((DefaultTableModel) tblFlavors.getModel()).setRowCount(0);
            ((DefaultTableModel) tblSizes.getModel()).setRowCount(0);
            categoriesList.setSelectedIndex(0);
            isEditing = false;
            editingProductId = -1;
            jLabel3.setText("Add Product");

            // Update table
            productTableModel.setRowCount(0);
            productIds.clear();
            Category selectedFilterCategory = (Category) categoriesList1.getSelectedItem();
            String categoryName = selectedFilterCategory != null ? selectedFilterCategory.getCategoryName() : "All";
            showProducts(productTableModel, categoryName, searchProduct.getText().trim());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    private void editProductActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Get the selected row index
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to edit.");
                return;
            }

            // Get the product ID from the internal list
            if (selectedRow >= productIds.size()) {
                JOptionPane.showMessageDialog(this, "Invalid product selection.");
                return;
            }
            editingProductId = productIds.get(selectedRow);

            // Find the product in ProductData
            Product selectedProduct = null;
            for (List<Product> products : ProductData.categorizedProducts.values()) {
                for (Product p : products) {
                    if (p.getId() == editingProductId) {
                        selectedProduct = p;
                        break;
                    }
                }
                if (selectedProduct != null) break;
            }

            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(this, "Product not found.");
                return;
            }

            // Populate the input fields with product details
            name.setText(selectedProduct.getProductName());

            // Set category
            for (int i = 0; i < categoriesList.getItemCount(); i++) {
                Category category = categoriesList.getItemAt(i);
                if (category.getId() == selectedProduct.getCategoryId()) {
                    categoriesList.setSelectedIndex(i);
                    break;
                }
            }

            // Populate flavors table
            DefaultTableModel flavorModel = (DefaultTableModel) tblFlavors.getModel();
            flavorModel.setRowCount(0);
            for (Flavor flavor : selectedProduct.getFlavors()) {
                flavorModel.addRow(new Object[] { flavor.getName() });
            }

            // Populate sizes table
            DefaultTableModel sizeModel = (DefaultTableModel) tblSizes.getModel();
            sizeModel.setRowCount(0);
            for (Size size : selectedProduct.getSizes()) {
                sizeModel.addRow(new Object[] { size.getName(), size.getPrice() });
            }

            // Update UI to indicate editing mode
            isEditing = true;
            jLabel3.setText("Edit Product");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String flavor = JOptionPane.showInputDialog(this, "Enter Flavor:");
        if (flavor == null || flavor.trim().isEmpty()) {
            flavor = "-";
        }
        DefaultTableModel model = (DefaultTableModel) tblFlavors.getModel();
        model.addRow(new Object[] { flavor });
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTextField sizeField = new JTextField(15);
        JTextField priceField = new JTextField(15);
        panel.add(new JLabel("Size Name:"));
        panel.add(sizeField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Enter Size Details",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String sizeName = sizeField.getText().trim();
            String priceInput = priceField.getText().trim();

            if (sizeName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Size name cannot be empty.");
                return;
            }

            Integer price = null;
            if (!priceInput.isEmpty()) {
                try {
                    price = Integer.parseInt(priceInput);
                    if (price < 0) {
                        JOptionPane.showMessageDialog(this, "Price cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price format.");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Price cannot be empty.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) tblSizes.getModel();
            model.addRow(new Object[] { sizeName, price });
        }
    }

    private void dltProductActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Get the selected row index
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to delete.");
                return;
            }

            // Get the product ID from the internal list
            if (selectedRow >= productIds.size()) {
                JOptionPane.showMessageDialog(this, "Invalid product selection.");
                return;
            }
            int productId = productIds.get(selectedRow);

            // Find product name for confirmation dialog
            String productName = (String) jTable1.getModel().getValueAt(selectedRow, 0);

            // Confirm deletion with the user
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the product: " + productName + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return; // User canceled the deletion
            }

            // Call API to delete the product
            String result = ApiClient.deleteProduct(productId);
            if (result.contains("Success")) {
                // Reload products from API
                ApiClient.loadProducts();

                // Refresh the table
                productTableModel.setRowCount(0);
                productIds.clear();
                Category selectedCategory = (Category) categoriesList1.getSelectedItem();
                String categoryName = selectedCategory != null ? selectedCategory.getCategoryName() : "All";
                showProducts(productTableModel, categoryName, searchProduct.getText().trim());

                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete product: " + result);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    private javax.swing.JScrollPane Sizes;
    private javax.swing.JButton addProduct;
    private javax.swing.JComboBox<Category> categoriesList;
    private javax.swing.JComboBox<Category> categoriesList1;
    private javax.swing.JButton dltProduct;
    private javax.swing.JButton editItem;
    private javax.swing.JScrollPane flavors;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblCategory1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField name;
    private javax.swing.JTextField searchProduct;
    private javax.swing.JTable tblFlavors;
    private javax.swing.JTable tblSizes;
}