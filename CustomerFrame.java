// CustomerFrame.java
// The customer-facing panel: browse menu, add to cart, view cart, generate bill.

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerFrame extends JFrame {

    private FoodOrderingSystem system;

    // ── Table models ──────────────────────────────────────────────────────────
    private DefaultTableModel menuTableModel;
    private DefaultTableModel cartTableModel;

    // ── Input fields ──────────────────────────────────────────────────────────
    private JTextField itemIdField;
    private JTextField quantityField;

    // ── Bill area ─────────────────────────────────────────────────────────────
    private JTextArea billArea;

    // ── Summary labels ────────────────────────────────────────────────────────
    private JLabel cartTotalLabel;

    public CustomerFrame(FoodOrderingSystem system) {
        this.system = system;
        initUI();
    }

    private void initUI() {
        setTitle("AS Restro – Order Food");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(true);

        // Root panel
        JPanel root = new JPanel(new BorderLayout(6, 6));
        root.setBackground(new Color(255, 245, 230));
        root.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        setContentPane(root);

        // ── TOP title bar ─────────────────────────────────────────────────────
        JLabel title = new JLabel("🍽  AS Restro – Place Your Order", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(new Color(200, 60, 40));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        root.add(title, BorderLayout.NORTH);

        // ── CENTRE: left = menu table, right = cart + bill ────────────────────
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildMenuPanel(), buildRightPanel());
        splitPane.setDividerLocation(420);
        splitPane.setResizeWeight(0.5);
        splitPane.setBackground(new Color(255, 245, 230));
        root.add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // ── LEFT: Menu table + add-to-cart controls ───────────────────────────────
    private JPanel buildMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBackground(new Color(255, 245, 230));

        JLabel lbl = new JLabel("📋  Menu", JLabel.LEFT);
        lbl.setFont(new Font("Serif", Font.BOLD, 17));
        lbl.setForeground(new Color(80, 30, 10));
        panel.add(lbl, BorderLayout.NORTH);

        // Menu table
        String[] menuCols = {"ID", "Item Name", "Category", "Price (Rs.)"};
        menuTableModel = new DefaultTableModel(menuCols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        refreshMenuTable();

        JTable menuTable = new JTable(menuTableModel);
        menuTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        menuTable.setRowHeight(24);
        menuTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        menuTable.getTableHeader().setBackground(new Color(200, 60, 40));
        menuTable.getTableHeader().setForeground(Color.WHITE);
        menuTable.setSelectionBackground(new Color(255, 200, 180));
        menuTable.setGridColor(new Color(220, 200, 180));

        // Column widths
        menuTable.getColumnModel().getColumn(0).setPreferredWidth(35);
        menuTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        menuTable.getColumnModel().getColumn(2).setPreferredWidth(110);
        menuTable.getColumnModel().getColumn(3).setPreferredWidth(90);

        JScrollPane menuScroll = new JScrollPane(menuTable);
        panel.add(menuScroll, BorderLayout.CENTER);

        // ── Add to cart controls ──────────────────────────────────────────────
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(new Color(255, 240, 218));
        addPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 160, 120)),
                "Add Item to Cart"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; addPanel.add(new JLabel("Item ID:"), g);
        g.gridx = 1; itemIdField = new JTextField(6); addPanel.add(itemIdField, g);

        g.gridx = 2; addPanel.add(new JLabel("Quantity:"), g);
        g.gridx = 3; quantityField = new JTextField(4); addPanel.add(quantityField, g);

        g.gridx = 4;
        JButton addBtn = makeRedButton("Add to Cart");
        addBtn.addActionListener(e -> handleAddToCart());
        addPanel.add(addBtn, g);

        panel.add(addPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ── RIGHT: Cart table + bill area ─────────────────────────────────────────
    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 8));
        panel.setBackground(new Color(255, 245, 230));

        // ── Cart section ──────────────────────────────────────────────────────
        JLabel cartLbl = new JLabel("🛒  Cart", JLabel.LEFT);
        cartLbl.setFont(new Font("Serif", Font.BOLD, 17));
        cartLbl.setForeground(new Color(80, 30, 10));

        String[] cartCols = {"Item Name", "Price (Rs.)", "Qty", "Subtotal (Rs.)"};
        cartTableModel = new DefaultTableModel(cartCols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable cartTable = new JTable(cartTableModel);
        cartTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cartTable.setRowHeight(24);
        cartTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        cartTable.getTableHeader().setBackground(new Color(60, 140, 80));
        cartTable.getTableHeader().setForeground(Color.WHITE);
        cartTable.setSelectionBackground(new Color(180, 230, 190));
        cartTable.setGridColor(new Color(200, 220, 200));

        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setPreferredSize(new Dimension(420, 200));

        cartTotalLabel = new JLabel("Cart Total: Rs. 0.00");
        cartTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cartTotalLabel.setForeground(new Color(60, 100, 60));
        cartTotalLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        JPanel cartTop = new JPanel(new BorderLayout());
        cartTop.setBackground(new Color(255, 245, 230));
        cartTop.add(cartLbl, BorderLayout.WEST);
        cartTop.add(cartTotalLabel, BorderLayout.EAST);

        JPanel cartPanel = new JPanel(new BorderLayout(4, 4));
        cartPanel.setBackground(new Color(255, 245, 230));
        cartPanel.add(cartTop, BorderLayout.NORTH);
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        // Buttons row
        JPanel cartBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        cartBtns.setBackground(new Color(255, 245, 230));

        JButton clearCartBtn = new JButton("Clear Cart");
        clearCartBtn.setForeground(new Color(200, 60, 40));
        clearCartBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        clearCartBtn.addActionListener(e -> handleClearCart());
        cartBtns.add(clearCartBtn);

        JButton genBillBtn = makeRedButton("Generate Bill");
        genBillBtn.addActionListener(e -> handleGenerateBill());
        cartBtns.add(genBillBtn);

        cartPanel.add(cartBtns, BorderLayout.SOUTH);

        // ── Bill section ──────────────────────────────────────────────────────
        JLabel billLbl = new JLabel("📄  Bill", JLabel.LEFT);
        billLbl.setFont(new Font("Serif", Font.BOLD, 17));
        billLbl.setForeground(new Color(80, 30, 10));

        billArea = new JTextArea(12, 36);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        billArea.setEditable(false);
        billArea.setBackground(new Color(250, 250, 240));
        billArea.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 140)));
        billArea.setText("  Bill will appear here after you click\n  'Generate Bill'.");

        JScrollPane billScroll = new JScrollPane(billArea);

        JPanel billPanel = new JPanel(new BorderLayout(4, 4));
        billPanel.setBackground(new Color(255, 245, 230));
        billPanel.add(billLbl, BorderLayout.NORTH);
        billPanel.add(billScroll, BorderLayout.CENTER);

        // ── Combine cart + bill vertically ────────────────────────────────────
        panel.add(cartPanel, BorderLayout.NORTH);
        panel.add(billPanel, BorderLayout.CENTER);

        return panel;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private JButton makeRedButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(200, 60, 40));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void refreshMenuTable() {
        menuTableModel.setRowCount(0);
        for (FoodItem item : system.getMenu()) {
            menuTableModel.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCategory(),
                String.format("%.2f", item.getPrice())
            });
        }
    }

    private void refreshCartTable() {
        cartTableModel.setRowCount(0);
        for (CartItem ci : system.getCart()) {
            cartTableModel.addRow(new Object[]{
                ci.getFoodItem().getName(),
                String.format("%.2f", ci.getFoodItem().getPrice()),
                ci.getQuantity(),
                String.format("%.2f", ci.getSubtotal())
            });
        }
        // Update total label
        double raw = system.calcRawTotal();
        cartTotalLabel.setText("Cart Total: Rs. " + String.format("%.2f", raw));
    }

    // ── Event handlers ────────────────────────────────────────────────────────

    private void handleAddToCart() {
        String idText  = itemIdField.getText().trim();
        String qtyText = quantityField.getText().trim();

        if (idText.isEmpty() || qtyText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both Item ID and Quantity.",
                    "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id, qty;
        try {
            id  = Integer.parseInt(idText);
            qty = Integer.parseInt(qtyText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Item ID and Quantity must be whole numbers.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (qty <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Quantity must be at least 1.",
                    "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean added = system.addToCart(id, qty);
        if (!added) {
            JOptionPane.showMessageDialog(this,
                    "Item ID " + id + " not found in the menu.",
                    "Item Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Item added to cart successfully!",
                "Added", JOptionPane.INFORMATION_MESSAGE);

        itemIdField.setText("");
        quantityField.setText("");
        refreshCartTable();
    }

    private void handleClearCart() {
        if (system.getCart().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is already empty.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to clear the cart?",
                "Clear Cart", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            system.clearCart();
            refreshCartTable();
            billArea.setText("  Cart cleared.");
        }
    }

    private void handleGenerateBill() {
        List<CartItem> cart = system.getCart();

        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Your cart is empty. Please add items before generating a bill.",
                    "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Snapshot values before generating order (which clears cart)
        double rawTotal  = system.calcRawTotal();
        double discount  = system.calcDiscount(rawTotal);
        double afterDisc = rawTotal - discount;
        double gst       = system.calcGST(afterDisc);
        double finalAmt  = system.calcFinalTotal(rawTotal, discount, gst);

        // Build bill text before clearing cart
        StringBuilder sb = new StringBuilder();
        sb.append("================================================\n");
        sb.append("           AS RESTRO - FOOD BILL               \n");
        sb.append("================================================\n");

        // Capture cart items now (before generateOrder clears the cart)
        String cartSnapshot = buildCartSnapshot(cart);

        // Generate order (clears cart, saves to sales log)
        int orderId = system.generateOrder();

        sb.append(String.format(" Order ID   : #%d%n", orderId));
        sb.append(String.format(" Date/Time  : %s%n", new java.util.Date()));
        sb.append("------------------------------------------------\n");
        sb.append(String.format(" %-22s %6s %8s%n", "Item", "Qty", "Amount"));
        sb.append("------------------------------------------------\n");
        sb.append(cartSnapshot);
        sb.append("------------------------------------------------\n");
        sb.append(String.format(" %-30s %8s%n", "Sub-Total:", String.format("Rs. %.2f", rawTotal)));

        if (discount > 0) {
            sb.append(String.format(" %-30s %8s%n", "Discount (10%):", String.format("- Rs. %.2f", discount)));
        } else {
            sb.append(String.format(" %-30s %8s%n", "Discount:", "None"));
        }

        sb.append(String.format(" %-30s %8s%n", "GST (5%):", String.format("+ Rs. %.2f", gst)));
        sb.append("================================================\n");
        sb.append(String.format(" %-30s %8s%n", "TOTAL PAYABLE:", String.format("Rs. %.2f", finalAmt)));
        sb.append("================================================\n");
        sb.append("        Thank you for dining with us!          \n");
        sb.append("================================================\n");

        billArea.setText(sb.toString());
        refreshCartTable();   // Cart is now empty; refresh table

        JOptionPane.showMessageDialog(this,
                "Order #" + orderId + " placed successfully!\nTotal: Rs. " + String.format("%.2f", finalAmt),
                "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Build a formatted string of cart items (call before clearing cart) */
    private String buildCartSnapshot(List<CartItem> cart) {
        StringBuilder sb = new StringBuilder();
        for (CartItem ci : cart) {
            sb.append(String.format(" %-22s %6d %8s%n",
                    ci.getFoodItem().getName(),
                    ci.getQuantity(),
                    String.format("Rs. %.2f", ci.getSubtotal())));
        }
        return sb.toString();
    }
}
