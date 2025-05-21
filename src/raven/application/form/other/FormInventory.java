package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logic.ApiClient;
import models.Inventory;

/**
 * @author Raven
 */
public class FormInventory extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private List<Inventory> inventoryList; // Store inventory list to track IDs

    public FormInventory() {
        try {
            ApiClient.login(); // Ensure token is obtained
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Failed to authenticate with API: " + e.getMessage(), 
                "Authentication Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
        putClientProperty("refreshable", true);  // Mark as refreshable
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        // Initialize table model
        tableModel = (DefaultTableModel) jTable1.getModel();
        // Load inventory data on initialization
        refreshData();
    }

    public void refreshData() {
        // Clear existing table data
        tableModel.setRowCount(0);
        
        // Load inventory data from API
        inventoryList = ApiClient.loadInventory();
        if (inventoryList != null) {
            // Populate table with inventory data
            for (Inventory inventory : inventoryList) {
                tableModel.addRow(new Object[]{
                    inventory.getName(),
                    inventory.getQuantity()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to load inventory data", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void addNewItem() {
        // Prompt for inventory name
        String name = JOptionPane.showInputDialog(
            this,
            "Enter Inventory Item Name:",
            "Add New Inventory Item",
            JOptionPane.PLAIN_MESSAGE
        );

        if (name == null || name.trim().isEmpty()) {
            return; // User cancelled or entered empty input
        }

        // Prompt for quantity
        String quantityStr = JOptionPane.showInputDialog(
            this,
            "Enter Quantity:",
            "Add New Inventory Item",
            JOptionPane.PLAIN_MESSAGE
        );

        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return; // User cancelled or entered empty input
        }

        try {
            int quantity = Integer.parseInt(quantityStr); // Validate quantity
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, 
                    "Quantity must be non-negative", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Inventory newInventory = new Inventory();
            newInventory.setName(name);
            newInventory.setQuantity(quantity);

            // Call API to store new inventory item
            boolean success = ApiClient.storeInventory(newInventory);
            if (success) {
                JOptionPane.showMessageDialog(
                    this,
                    "Inventory item added successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                refreshData(); // Refresh table after adding
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Failed to add inventory item",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Invalid quantity format: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error adding inventory item: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteSelectedItem() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select an inventory item to delete",
                "Warning",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Get ID from inventoryList (since ID is not in table)
        int inventoryId = inventoryList.get(selectedRow).getId();
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this inventory item (Name: " + 
                inventoryList.get(selectedRow).getName() + ")?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Call deleteInventory
                boolean success = ApiClient.deleteInventory(inventoryId);
                if (success) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Inventory item deleted successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    refreshData(); // Refresh table after deletion
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete inventory item",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error deleting inventory item: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void saveEditedItems() {
        try {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String name = (String) tableModel.getValueAt(row, 0);
                Object quantityObj = tableModel.getValueAt(row, 1);
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Name cannot be empty for row " + (row + 1),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (quantityObj == null) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Quantity cannot be empty for row " + (row + 1),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityObj.toString());
                    if (quantity < 0) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Quantity must be non-negative for row " + (row + 1),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Invalid quantity format for row " + (row + 1),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                int inventoryId = inventoryList.get(row).getId();
                Inventory updatedInventory = new Inventory();
                updatedInventory.setName(name);
                updatedInventory.setQuantity(quantity);

                // Call updateInventory
                boolean success = ApiClient.updateInventory(inventoryId, updatedInventory);
                if (!success) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Failed to update inventory item: " + name,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }

            JOptionPane.showMessageDialog(
                this,
                "Inventory updates saved successfully",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            refreshData(); // Refresh table after saving
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error saving inventory updates: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Inventory");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, true
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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton1.setText("Add Item");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Delete Item");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Save Editing");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addNewItem();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        deleteSelectedItem();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        saveEditedItems();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}