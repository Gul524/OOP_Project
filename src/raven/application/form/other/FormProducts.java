package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import logic.ApiClient;
import models.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Category;
import data.ProductData;
import java.awt.Component;

import models.Flavor;
import models.Size;

/**
 *
 * @author anas
 */
public class FormProducts extends javax.swing.JPanel {
    DefaultTableModel productTableModel = new DefaultTableModel( new Object [][] {

    },
            new String [] {
                    "ID", "Name", "Size","Flavor"
            }) {
        Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
        };
        boolean[] canEdit = new boolean [] {
                false, false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    } ;


    public FormProducts() {
        initComponents();
        loadProducts();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

    }



    void showProducts(DefaultTableModel tableModel , String category ) {
        try {

            if ("All".equals(category)) {
                for (List<Product> products : ProductData.categorizedProducts.values()) {
                    for (Product p : products) {
                        tableModel.addRow(new Object[]{
                                p.getId(),
                                p.getProductName(),
                                p.getSizesString(),
                                p.getFalovorsString()

                        });
                    }
                }
                return;
            }
            if(ProductData.categorizedProducts.get(category).isEmpty()){
                return;
            }
            for(Product p : ProductData.categorizedProducts.get(category)){
                tableModel.addRow(
                        new Object[]{
                                p.getId(),
                                p.getProductName(),
                                p.getSizesString(),
                                p.getFalovorsString()

                        }
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }



    void loadProducts() {
    categoriesList.setModel(new DefaultComboBoxModel<>(ProductData.categories.toArray(new Category[0])));

    showProducts(productTableModel ,"All");
    
    // Set a custom renderer for the JComboBox
    categoriesList.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Category) {
                setText(((Category) value).getCategoryName()); // Display only categoryName
            }
            return this;
        }
    });
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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


//        categoriesList1.actionPerformed();

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setModel(productTableModel);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
//            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

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
            new Object [][] {

            },
            new String [] {
                "Flavors"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblFlavors.getTableHeader().setReorderingAllowed(false);
        flavors.setViewportView(tblFlavors);
        if (tblFlavors.getColumnModel().getColumnCount() > 0) {
            tblFlavors.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel2.setText("Sizes:");

        tblSizes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Size", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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

        lblCategory.setText("Category:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Add Product");
        jLabel3.setToolTipText("");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
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
                .addGap(26, 26, 26))
        );

        lblSearch.setText("Search Product:");

        lblCategory1.setText("Category:");

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
                .addContainerGap())
        );
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductActionPerformed

        try {

            // Get product name
            String name = JOptionPane.showInputDialog(this, "Enter Product Name:");
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product Name cannot be empty.");
                return;
            }

            // Get product price
            String priceStr = JOptionPane.showInputDialog(this, "Enter Product Price:");
            if (priceStr == null || priceStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product Price cannot be empty.");
                return;
            }
            int price = Integer.parseInt(priceStr.trim());

            // Normally, you'd ask for Size and Flavor, but let's keep them empty for now
            ArrayList<Size> sizes = new ArrayList<>();
            ArrayList<Flavor> flavors = new ArrayList<>();

            // Create Product and add to products list
//            Product product = new Product(categories, name, price, sizes, flavors);
//            products.add(product);
            JOptionPane.showMessageDialog(this, "Product added successfully!");

            // OPTIONAL: Update JTable here to show the new product
            // (you can implement it if you want)
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }//GEN-LAST:event_addProductActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void dltProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dltProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dltProductActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Sizes;
    private javax.swing.JButton addProduct;
    private javax.swing.JComboBox<Category
    > categoriesList;
    private javax.swing.JComboBox<Category
    > categoriesList1;
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
    // End of variables declaration//GEN-END:variables
}
