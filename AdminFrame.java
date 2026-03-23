// AdminFrame.java
// Admin dashboard: shows total orders, total revenue, and a full sales log table.

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {

    private FoodOrderingSystem system;
    private DefaultTableModel salesTableModel;
    private JLabel totalOrdersLabel;
    private JLabel totalRevenueLabel;

    public AdminFrame(FoodOrderingSystem system) {
        this.system = system;
        initUI();
    }

    private void initUI() {
        setTitle("AS Restro – Admin Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 540);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBackground(new Color(230, 240, 255));
        root.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        setContentPane(root);

        // ── TOP BAR ───────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(30, 70, 140));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        JLabel title = new JLabel("🔑  Admin Dashboard", JLabel.LEFT);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        topBar.add(title, BorderLayout.WEST);

        JButton refreshBtn = new JButton("⟳ Refresh");
        refreshBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBackground(new Color(20, 50, 110));
        refreshBtn.setFocusPainted(false);
        refreshBtn.setContentAreaFilled(false);
        refreshBtn.setOpaque(true);
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> refreshData());
        topBar.add(refreshBtn, BorderLayout.EAST);

        root.add(topBar, BorderLayout.NORTH);

        // ── SUMMARY CARDS ─────────────────────────────────────────────────────
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        statsPanel.setBackground(new Color(230, 240, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        totalOrdersLabel  = new JLabel("Total Orders: 0", JLabel.CENTER);
        totalRevenueLabel = new JLabel("Total Revenue: Rs. 0.00", JLabel.CENTER);

        styleStatCard(totalOrdersLabel,  "📦  Total Orders",  new Color(255, 245, 200), new Color(180, 130, 0));
        styleStatCard(totalRevenueLabel, "💰  Total Revenue", new Color(220, 255, 220), new Color(0, 130, 50));

        statsPanel.add(wrapInCard(totalOrdersLabel,  "📦  Total Orders",  new Color(255, 245, 200)));
        statsPanel.add(wrapInCard(totalRevenueLabel, "💰  Total Revenue", new Color(220, 255, 220)));

        root.add(statsPanel, BorderLayout.NORTH);  // Will be placed below topBar using compound panel

        // Re-arrange: use a north compound panel for topBar + stats
        JPanel northSection = new JPanel(new BorderLayout(0, 8));
        northSection.setBackground(new Color(230, 240, 255));
        northSection.add(topBar,    BorderLayout.NORTH);
        northSection.add(statsPanel, BorderLayout.SOUTH);
        root.add(northSection, BorderLayout.NORTH);

        // ── SALES TABLE ───────────────────────────────────────────────────────
        JLabel tableTitle = new JLabel("📊  Sales Log", JLabel.LEFT);
        tableTitle.setFont(new Font("Serif", Font.BOLD, 17));
        tableTitle.setForeground(new Color(20, 50, 120));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        String[] cols = {"Order ID", "Item Name", "Qty", "Unit Price (Rs.)", "Total (Rs.)"};
        salesTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable salesTable = new JTable(salesTableModel);
        salesTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        salesTable.setRowHeight(24);
        salesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        salesTable.getTableHeader().setBackground(new Color(30, 70, 140));
        salesTable.getTableHeader().setForeground(Color.WHITE);
        salesTable.setSelectionBackground(new Color(180, 200, 240));
        salesTable.setGridColor(new Color(180, 200, 230));
        salesTable.setBackground(Color.WHITE);

        salesTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        salesTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        salesTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        salesTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        salesTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 220)));

        JPanel tablePanel = new JPanel(new BorderLayout(4, 4));
        tablePanel.setBackground(new Color(230, 240, 255));
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        root.add(tablePanel, BorderLayout.CENTER);

        // ── FOOTER / CLOSE BUTTON ─────────────────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(new Color(230, 240, 255));
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        closeBtn.addActionListener(e -> dispose());
        footer.add(closeBtn);
        root.add(footer, BorderLayout.SOUTH);

        // Initial data load
        refreshData();
        setVisible(true);
    }

    // ── Refresh all data from system ──────────────────────────────────────────
    private void refreshData() {
        int totalOrders     = system.getTotalOrders();
        double totalRevenue = system.getTotalRevenue();

        totalOrdersLabel.setText(String.valueOf(totalOrders));
        totalRevenueLabel.setText(String.format("Rs. %.2f", totalRevenue));

        // Rebuild sales table
        salesTableModel.setRowCount(0);
        List<Sale> sales = system.getSalesLog();
        if (sales.isEmpty()) {
            salesTableModel.addRow(new Object[]{"–", "No sales recorded yet.", "–", "–", "–"});
        } else {
            for (Sale s : sales) {
                salesTableModel.addRow(new Object[]{
                    "#" + s.getOrderId(),
                    s.getItemName(),
                    s.getQuantity(),
                    String.format("%.2f", s.getPricePerItem()),
                    String.format("%.2f", s.getTotalAmount())
                });
            }
        }
    }

    // ── UI helpers ────────────────────────────────────────────────────────────

    private void styleStatCard(JLabel lbl, String title, Color bg, Color fg) {
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl.setForeground(fg);
    }

    private JPanel wrapInCard(JLabel valueLabel, String cardTitle, Color bg) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230), 1),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)));

        JLabel titleLbl = new JLabel(cardTitle, JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        titleLbl.setForeground(new Color(80, 80, 100));

        card.add(titleLbl,   BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }
}
