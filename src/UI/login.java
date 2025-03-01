package UI;
import constraints.*;
public class login extends javax.swing.JFrame {

    public login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel backgroundPanel = new javax.swing.JPanel();
        // Variables declaration - do not modify//GEN-BEGIN:variables
        javax.swing.JPanel backgroundLoginPanel = new javax.swing.JPanel();
        javax.swing.JTextField usernameField = new javax.swing.JTextField();
        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField();
        javax.swing.JButton loginButton = new javax.swing.JButton();
        javax.swing.JLabel loginLabel = new javax.swing.JLabel();
        javax.swing.JLabel usernameLabel = new javax.swing.JLabel();
        javax.swing.JLabel passwordLabel = new javax.swing.JLabel();
        javax.swing.JLabel topLeftLabel = new javax.swing.JLabel();
        javax.swing.JLabel welcomeLabel = new javax.swing.JLabel();
        javax.swing.JLabel subtitle = new javax.swing.JLabel();
        javax.swing.JLabel memeberLabel = new javax.swing.JLabel();
        javax.swing.JLabel memeberlabel1 = new javax.swing.JLabel();
        javax.swing.JLabel memberlabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("loginFrame");
        setBackground(java.awt.Color.white);
        setBounds(new java.awt.Rectangle(50, 50, 750, 550));
        setMaximumSize(new java.awt.Dimension(2147, 2147));
        setPreferredSize(new java.awt.Dimension(750, 550));
        setResizable(false);
        setSize(new java.awt.Dimension(750, 550));
        getContentPane().setLayout(null);

        backgroundPanel.setBackground(colors.primary);
        backgroundPanel.setMaximumSize(new java.awt.Dimension(750, 550));
        backgroundPanel.setMinimumSize(new java.awt.Dimension(750, 550));
        backgroundPanel.setPreferredSize(new java.awt.Dimension(750, 550));

        backgroundLoginPanel.setBackground(colors.secondary);
        backgroundLoginPanel.setBorder(new javax.swing.border.LineBorder(colors.primary, 3, true));
        backgroundLoginPanel.setMaximumSize(new java.awt.Dimension(450, 550));
        backgroundLoginPanel.setPreferredSize(new java.awt.Dimension(450, 550));

        usernameField.setBackground(colors.secondary);
        usernameField.setForeground(colors.textWithSecondaryBg);
        usernameField.setBorder(new javax.swing.border.LineBorder(colors.primary, 2, true));
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        passwordField.setBackground(colors.secondary);
        passwordField.setForeground(colors.textWithSecondaryBg);
        passwordField.setBorder(new javax.swing.border.LineBorder(colors.primary, 2, true));

        loginButton.setBackground(colors.primary);
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        loginButton.setForeground(colors.textWithPrimaryBg);
        loginButton.setText("Log In");

        loginLabel.setBackground(colors.secondary);
        loginLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        loginLabel.setForeground(colors.textWithSecondaryBg);
        loginLabel.setText("Login");

        usernameLabel.setBackground(colors.secondary);
        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        usernameLabel.setForeground(colors.textWithSecondaryBg);
        usernameLabel.setText("Username");

        passwordLabel.setBackground(colors.secondary);
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        passwordLabel.setForeground(colors.textWithSecondaryBg);
        passwordLabel.setText("Password");

        javax.swing.GroupLayout backgroundLoginPanelLayout = new javax.swing.GroupLayout(backgroundLoginPanel);
        backgroundLoginPanel.setLayout(backgroundLoginPanelLayout);
        backgroundLoginPanelLayout.setHorizontalGroup(
            backgroundLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLoginPanelLayout.createSequentialGroup()
                .addGroup(backgroundLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLoginPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(backgroundLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(backgroundLoginPanelLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(loginLabel))
                    .addGroup(backgroundLoginPanelLayout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        backgroundLoginPanelLayout.setVerticalGroup(
            backgroundLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLoginPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(loginLabel)
                .addGap(79, 79, 79)
                .addComponent(usernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        topLeftLabel.setBackground(colors.primary);
        topLeftLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        topLeftLabel.setForeground(colors.textWithPrimaryBg);
        topLeftLabel.setText("POS");

        welcomeLabel.setBackground(colors.primary);
        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        welcomeLabel.setForeground(colors.textWithPrimaryBg);
        welcomeLabel.setText("Welcome");

        subtitle.setBackground(colors.primary);
        subtitle.setFont(new java.awt.Font("Lucida Calligraphy", 3, 18)); // NOI18N
        subtitle.setForeground(colors.textWithPrimaryBg);
        subtitle.setText("A Project of OOP");

        memeberLabel.setBackground(colors.primary);
        memeberLabel.setFont(new java.awt.Font("Microsoft Himalaya", 3, 20)); // NOI18N
        memeberLabel.setForeground(colors.textWithPrimaryBg);
        memeberLabel.setText("AMNA ASHRAF  -  ");

        memeberlabel1.setBackground(colors.primary);
        memeberlabel1.setFont(new java.awt.Font("Microsoft Himalaya", 3, 20)); // NOI18N
        memeberlabel1.setForeground(colors.textWithPrimaryBg);
        memeberlabel1.setText("SULEMAN GUL  -  041");

        memberlabel2.setBackground(colors.primary);
        memberlabel2.setFont(new java.awt.Font("Microsoft Himalaya", 3, 20)); // NOI18N
        memberlabel2.setForeground(colors.textWithPrimaryBg);
        memberlabel2.setText("MUHAMMAD ANAS  -  068");

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(memeberlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144))
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(topLeftLabel)
                                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(subtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(memeberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(memberlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backgroundLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(topLeftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(welcomeLabel)
                .addGap(12, 12, 12)
                .addComponent(subtitle)
                .addGap(197, 197, 197)
                .addComponent(memeberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memeberlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memberlabel2)
                .addContainerGap(57, Short.MAX_VALUE))
            .addComponent(backgroundLoginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(backgroundPanel);
        backgroundPanel.setBounds(1, 0, 750, 550);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // End of variables declaration//GEN-END:variables
}
