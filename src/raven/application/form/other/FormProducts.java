package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JOptionPane;
import models.Product;
import java.util.ArrayList;
import models.Flavor;
import models.Size;

/**
 *
 * @author Raven
 */
public class FormProducts extends javax.swing.JPanel {

    private ArrayList<Product> products = new ArrayList<>();

    public FormProducts() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lb = new javax.swing.JLabel();
        editItem = new javax.swing.JButton();
        addItem = new javax.swing.JButton();
        dltItem = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Flavor", "Size", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Products");

        editItem.setText("Edit Item");

        addItem.setText("Add Item");
        addItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemActionPerformed(evt);
            }
        });

        dltItem.setText("Delete Item");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addComponent(addItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dltItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editItem)
                .addContainerGap(428, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dltItem)
                    .addComponent(addItem)
                    .addComponent(editItem))
                .addContainerGap(38, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemActionPerformed

        try {
            // Get product ID
            String idStr = JOptionPane.showInputDialog(this, "Enter Product ID:");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product ID cannot be empty.");
                return;
            }
            int id = Integer.parseInt(idStr.trim());

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
            Product product = new Product(id, name, price, sizes, flavors);
            products.add(product);

            JOptionPane.showMessageDialog(this, "Product added successfully!");

            // OPTIONAL: Update JTable here to show the new product
            // (you can implement it if you want)
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }//GEN-LAST:event_addItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItem;
    private javax.swing.JButton dltItem;
    private javax.swing.JButton editItem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}
