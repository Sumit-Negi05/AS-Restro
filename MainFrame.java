// MainFrame.java
// The first screen users see — contains "Order Food" and "Admin Login" buttons.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private FoodOrderingSystem system;

    public MainFrame() {
        system = new FoodOrderingSystem();
        initUI();
    }

    private void initUI() {
        setTitle("AS Restro - Food Ordering System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 420);
        setLocationRelativeTo(null);   // Center on screen
        setResizable(false);

        // ── Main panel with background colour ───────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 245, 230));   // warm cream
        setContentPane(mainPanel);

        // ── TOP BAR ─────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(200, 60, 40));         // deep red
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("🍽  AS Restro");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topBar.add(titleLabel, BorderLayout.WEST);

        JButton adminBtn = new JButton("Admin Login");
        adminBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setBackground(new Color(140, 30, 20));
        adminBtn.setFocusPainted(false);
        adminBtn.setContentAreaFilled(false);
        adminBtn.setOpaque(true);
        adminBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        adminBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        topBar.add(adminBtn, BorderLayout.EAST);

        mainPanel.add(topBar, BorderLayout.NORTH);

        // ── CENTER AREA ──────────────────────────────────────────────────────
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(255, 245, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 6, 0);

        JLabel welcomeLabel = new JLabel("Welcome to AS Restro!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(80, 30, 10));
        centerPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        JLabel subLabel = new JLabel("Delicious food, delivered fresh to your table.");
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subLabel.setForeground(new Color(120, 80, 50));
        centerPanel.add(subLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 10, 0);
        JButton orderBtn = new JButton("🛒  Order Food");
        orderBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        orderBtn.setForeground(Color.WHITE);
        orderBtn.setBackground(new Color(200, 60, 40));
        orderBtn.setFocusPainted(false);
        orderBtn.setContentAreaFilled(false);
        orderBtn.setOpaque(true);
        orderBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        orderBtn.setPreferredSize(new Dimension(220, 55));
        orderBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        centerPanel.add(orderBtn, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ── FOOTER ───────────────────────────────────────────────────────────
        JLabel footer = new JLabel("© 2024 AS Restro  |  College Lab Project", JLabel.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 11));
        footer.setForeground(new Color(160, 110, 80));
        footer.setBorder(BorderFactory.createEmptyBorder(6, 0, 8, 0));
        mainPanel.add(footer, BorderLayout.SOUTH);

        // ── Button actions ────────────────────────────────────────────────────
        orderBtn.addActionListener(e -> openCustomerFrame());
        adminBtn.addActionListener(e -> showAdminLogin());

        setVisible(true);
    }

    // Opens the Customer ordering panel
    private void openCustomerFrame() {
        new CustomerFrame(system);
    }

    // Shows admin login dialog; on success opens AdminFrame
    private void showAdminLogin() {
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 8, 8));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);

        int result = JOptionPane.showConfirmDialog(
                this, loginPanel, "Admin Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (username.equals("admin") && password.equals("ASrestro")) {
                new AdminFrame(system);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password!\nPlease try again.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Application entry point ───────────────────────────────────────────────
    public static void main(String[] args) {
        // Use the system look & feel for a native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
