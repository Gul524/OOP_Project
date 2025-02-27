package UI;

public class login extends javax.swing.JFrame {

    public login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        backgroundLoginPanel = new javax.swing.JPanel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        loginLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        topLeftLabel = new javax.swing.JLabel();
        welcomeLabel = new javax.swing.JLabel();
        subtitle = new javax.swing.JLabel();
        memeberLabel = new javax.swing.JLabel();
        memeberlabel = new javax.swing.JLabel();
        memberlabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("loginFrame");
        setBackground(java.awt.Color.white);
        setBounds(new java.awt.Rectangle(50, 50, 750, 550));
        setMaximumSize(new java.awt.Dimension(2147, 2147));
        setPreferredSize(new java.awt.Dimension(750, 550));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 500));
        getContentPane().setLayout(null);

        backgroundPanel.setBackground(new java.awt.Color(0, 102, 102));
        backgroundPanel.setForeground(new java.awt.Color(0, 255, 102));
        backgroundPanel.setMaximumSize(new java.awt.Dimension(750, 550));
        backgroundPanel.setMinimumSize(new java.awt.Dimension(750, 550));
        backgroundPanel.setPreferredSize(new java.awt.Dimension(750, 550));

        backgroundLoginPanel.setBackground(new java.awt.Color(255, 255, 255));
        backgroundLoginPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 3, true));
        backgroundLoginPanel.setMaximumSize(new java.awt.Dimension(450, 550));
        backgroundLoginPanel.setPreferredSize(new java.awt.Dimension(450, 550));

        usernameField.setBackground(new java.awt.Color(255, 255, 255));
        usernameField.setForeground(new java.awt.Color(0, 0, 0));
        usernameField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 2, true));
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        passwordField.setBackground(new java.awt.Color(255, 255, 255));
        passwordField.setForeground(new java.awt.Color(0, 0, 0));
        passwordField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 2, true));

        loginButton.setBackground(new java.awt.Color(0, 102, 102));
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Log In");

        loginLabel.setBackground(new java.awt.Color(255, 255, 255));
        loginLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        loginLabel.setForeground(new java.awt.Color(0, 102, 102));
        loginLabel.setText("Login");

        usernameLabel.setBackground(new java.awt.Color(255, 255, 255));
        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(0, 0, 0));
        usernameLabel.setText("Username");

        passwordLabel.setBackground(new java.awt.Color(255, 255, 255));
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(0, 0, 0));
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

        topLeftLabel.setBackground(new java.awt.Color(0, 102, 102));
        topLeftLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        topLeftLabel.setForeground(new java.awt.Color(255, 255, 255));
        topLeftLabel.setText("POS");

        welcomeLabel.setBackground(new java.awt.Color(0, 102, 102));
        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        welcomeLabel.setForeground(new java.awt.Color(255, 255, 255));
        welcomeLabel.setText("Welcome");

        subtitle.setBackground(new java.awt.Color(0, 102, 102));
        subtitle.setFont(new java.awt.Font("Lucida Calligraphy", 2, 18)); // NOI18N
        subtitle.setForeground(new java.awt.Color(255, 255, 255));
        subtitle.setText("A Project of OOP");

        memeberLabel.setBackground(new java.awt.Color(0, 102, 102));
        memeberLabel.setFont(new java.awt.Font("Microsoft Himalaya", 2, 18)); // NOI18N
        memeberLabel.setForeground(new java.awt.Color(255, 255, 255));
        memeberLabel.setText("AMNA ASHRAF  -  ");

        memeberlabel.setBackground(new java.awt.Color(0, 102, 102));
        memeberlabel.setFont(new java.awt.Font("Microsoft Himalaya", 2, 18)); // NOI18N
        memeberlabel.setForeground(new java.awt.Color(255, 255, 255));
        memeberlabel.setText("SULEMAN GUL  -  041");

        memberlabel.setBackground(new java.awt.Color(0, 102, 102));
        memberlabel.setFont(new java.awt.Font("Microsoft Himalaya", 2, 18)); // NOI18N
        memberlabel.setForeground(new java.awt.Color(255, 255, 255));
        memberlabel.setText("MUHAMMAD ANAS  -  068");

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(memeberlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(memeberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(memberlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(memeberlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memberlabel)
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundLoginPanel;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JLabel memberlabel;
    private javax.swing.JLabel memeberLabel;
    private javax.swing.JLabel memeberlabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel subtitle;
    private javax.swing.JLabel topLeftLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
