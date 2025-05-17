package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import data.ProductData;
import logic.ApiClient;
import models.Product;
import models.Staff;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Raven
 */
public class FormStaff extends javax.swing.JPanel {

    List<Staff> forSendStaff = new ArrayList<>();

    public FormStaff() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        // Initialize table with all staff
        searchStaff("");
    }

    DefaultTableModel staffTableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Name", "CNIC", "Role"}) {
        Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean[]{
                false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };

    void searchStaff(String searchText) {
        // Clear existing rows
        staffTableModel.setRowCount(0);

        // Handle null or empty employee list
        if (ProductData.employees == null || ProductData.employees.isEmpty()) {
            return;
        }

        // Filter and add rows
        String query = searchText == null ? "" : searchText.trim().toLowerCase();
        for (Staff e : ProductData.employees) {
            if (query.isEmpty() || e.getName().toLowerCase().contains(query)) {
                staffTableModel.addRow(new Object[]{
                        e.getId(),
                        e.getName(),
                        e.getCnic(),
                        e.getRole()
                });
            }
        }
    }

    private void filterTableBySearch() {
        // This method seems unrelated to staff; it might belong to another form.
        // If needed for staff, it should be revised to work with staffTableModel.
        // For now, it's left as is since it's not called in the provided code.
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lb = new javax.swing.JLabel();
        staff = new javax.swing.JScrollPane();
        tblStaff = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        lblCNIC = new javax.swing.JLabel();
        cnic = new javax.swing.JTextField();
        lblRole = new javax.swing.JLabel();
        roles = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        searchStaff = new javax.swing.JTextField();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Staff");

        staff.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblStaff.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblStaff.setModel(staffTableModel);
        staff.setViewportView(tblStaff);
        if (tblStaff.getColumnModel().getColumnCount() > 0) {
            tblStaff.getColumnModel().getColumn(0).setResizable(false);
            tblStaff.getColumnModel().getColumn(1).setResizable(false);
            tblStaff.getColumnModel().getColumn(2).setResizable(false);
            tblStaff.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblName.setText("Name:");
        lblCNIC.setText("CNIC:");
        lblRole.setText("Role:");
        roles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Manager", "Waiter", "Cashier", "Cook"}));

        jButton1.setText("Add Staff");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String _name = name.getText().trim(); // Use getText() instead of getName()
                String _cnicText = cnic.getText().trim();
                String _role = (String) roles.getSelectedItem();

                // Validate inputs
                if (_name.isEmpty() || _cnicText.isEmpty() || _role == null) {
                    javax.swing.JOptionPane.showMessageDialog(FormStaff.this, "Please fill all fields.");
                    return;
                }

                try {
                    int _cnic = Integer.parseInt(_cnicText); // Safe string-to-int conversion
                    forSendStaff.add(new Staff(_name, _cnic, _role));
                    boolean isStaffAdded = ApiClient.storeStaff(forSendStaff);
                    if (isStaffAdded) {
                        ApiClient.loadStaff();
                        searchStaff(searchStaff.getText()); // Refresh table with current search
                        name.setText("");
                        cnic.setText("");
                        roles.setSelectedIndex(0);
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(FormStaff.this, "Failed to add staff.");
                    }
                } catch (NumberFormatException ex) {
                    javax.swing.JOptionPane.showMessageDialog(FormStaff.this, "Invalid CNIC format. Please enter a number.");
                }
            }
        });

        jButton2.setText("Delete Staff");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Implement delete functionality
                javax.swing.JOptionPane.showMessageDialog(FormStaff.this, "Delete functionality not implemented.");
            }
        });

        jButton3.setText("Edit Staff");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Implement edit functionality
                javax.swing.JOptionPane.showMessageDialog(FormStaff.this, "Edit functionality not implemented.");
            }
        });

        searchStaff.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchStaff(searchStaff.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchStaff(searchStaff.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchStaff(searchStaff.getText());
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Add New Staff");

        lblSearch.setText("Search Staff:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(55, 55, 55)
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton3))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(lblCNIC, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cnic, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(roles, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(67, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblName)
                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCNIC)
                                        .addComponent(cnic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblRole)
                                        .addComponent(roles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(staff, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(336, 336, 336)
                                .addComponent(lblSearch)
                                .addGap(18, 18, 18)
                                .addComponent(searchStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSearch)
                                        .addComponent(searchStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(staff, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
    }

    // Variables declaration
    private javax.swing.JTextField cnic;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lblCNIC;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField name;
    private javax.swing.JComboBox<String> roles;
    private javax.swing.JTextField searchStaff;
    private javax.swing.JScrollPane staff;
    private javax.swing.JTable tblStaff;
}