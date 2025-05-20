/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Category;
import logic.ApiClient;
import data.ProductData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anasj
 */
public class FormCategories extends javax.swing.JPanel {

    private DefaultTableModel tableModel;

    public FormCategories() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        initializeTable();
        loadCategories();
    }

    /**
     * Initializes the table model with Category ID and Category Name columns.
     */
    private void initializeTable() {
        String[] columnNames = {"Category Name"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false; // Prevents editing but allows selection
            }
        };
        jTable1.setModel(tableModel);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setColumnSelectionAllowed(false);
    }

    /**
     * Loads categories from ApiClient or ProductData and populates the table.
     */
    private void loadCategories() {
        try {
            // Try to fetch from ApiClient first
            List<Category> categories = ApiClient.loadCategories();
            tableModel.setRowCount(0); // Clear existing rows
            if (categories != null && !categories.isEmpty()) {
                for (Category category : categories) {
                    tableModel.addRow(new Object[]{
                        category.getCategoryName()
                    });
                }
            } else {
                // Fallback to ProductData.stringCategories
                List<String> stringCategories = ProductData.stringCategories;
                System.out.println("Loaded categories: " + ProductData.stringCategories);
                if (stringCategories != null) {
                    int idCounter = 1;
                    for (String categoryName : stringCategories) {
                        tableModel.addRow(new Object[]{
                            "C" + String.format("%03d", idCounter++), // Generate a simple ID (e.g., C001)
                            categoryName
                        });
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading categories: Not connected to internet",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            e.getMessage();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lb = new javax.swing.JLabel();
        addItem = new javax.swing.JButton();
        dltItem = new javax.swing.JButton();
        editItem = new javax.swing.JButton();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Product Categories");

        addItem.setText("Add Category");
        addItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemActionPerformed(evt);
            }
        });

        dltItem.setText("Delete Category");
        dltItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dltItemActionPerformed(evt);
            }
        });

        editItem.setText("Edit Category");
        editItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItemActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Category ID", "Category Name"}
        ) {
            Class[] types = new Class[]{java.lang.String.class, java.lang.String.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 300, Short.MAX_VALUE)
                                                .addComponent(addItem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(dltItem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(editItem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 300, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addItem)
                                        .addComponent(dltItem)
                                        .addComponent(editItem))
                                .addContainerGap(38, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    private void addItemActionPerformed(java.awt.event.ActionEvent evt) {
        // Get user input

        String catName = JOptionPane.showInputDialog(this, "Enter Category Name:");
        if (catName == null || catName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<Category> categoriesToSave = new ArrayList<>();
            Category newCategory = new Category(catName);
            categoriesToSave.add(newCategory);

            // Save to database
            ApiClient.storeCategory(categoriesToSave);

            int catId = tableModel.getRowCount() + 1;
            // Add to table
            tableModel.addRow(new Object[]{catName});
            JOptionPane.showMessageDialog(this, "Category added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error adding category: Not connected to internet",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            e.getMessage();
        }
    }

    private void dltItemActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int catID = (int) tableModel.getValueAt(selectedRow, 0);
        String catName = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete category: " + catName + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Delete from backend
                ApiClient.deleteCategory(catID);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Category deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error deleting category: Not connected to internet",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                e.getMessage();
            }
        }
    }

    private void editItemActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = tableModel.getValueAt(selectedRow, 0);
        String oldCatName = (String) tableModel.getValueAt(selectedRow, 1);

        String newCatName = JOptionPane.showInputDialog(this, "Enter new Category Name:", oldCatName);
        if (newCatName == null || newCatName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Send update request to backend
            int catId;
            if (idObj instanceof Integer) {
                catId = (Integer) idObj;
            } else if (idObj instanceof String) {
                // If IDs are strings like "C001", "C002"
                String idStr = ((String) idObj).replaceAll("[^\\d]", "");
                catId = Integer.parseInt(idStr);
            } else {
                throw new IllegalArgumentException("Unknown ID format: " + idObj);
            }

            ApiClient.updateCategory(catId, newCatName); // <-- call the proper method to update category

            // Update the table
            tableModel.setValueAt(newCatName, selectedRow, 1);

            JOptionPane.showMessageDialog(this, "Category updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error updating category: Not connected to internet",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            e.getMessage();
        }

    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton addItem;
    private javax.swing.JButton dltItem;
    private javax.swing.JButton editItem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb;
    // End of variables declaration                   
}
