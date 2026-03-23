// FoodOrderingSystem.java
// Core business logic: manages menu, cart, sales records, and order IDs

import java.util.ArrayList;
import java.util.List;

public class FoodOrderingSystem {

    // ── In-memory storage ────────────────────────────────────────────────────
    private List<FoodItem> menu       = new ArrayList<>();
    private List<CartItem> cart       = new ArrayList<>();
    private List<Sale>     salesLog   = new ArrayList<>();

    private int nextOrderId = 1001;   // Auto-incrementing order ID

    // ── Constructor: seed the menu ───────────────────────────────────────────
    public FoodOrderingSystem() {
        loadMenu();
    }

    private void loadMenu() {
        // 15 food items across different categories
        menu.add(new FoodItem(1,  "Veg Burger",          "Snacks",      120.00));
        menu.add(new FoodItem(2,  "Chicken Burger",       "Snacks",      160.00));
        menu.add(new FoodItem(3,  "Paneer Pizza (6\")",   "Pizza",       220.00));
        menu.add(new FoodItem(4,  "Chicken Pizza (6\")",  "Pizza",       280.00));
        menu.add(new FoodItem(5,  "Veg Fried Rice",       "Rice",        150.00));
        menu.add(new FoodItem(6,  "Chicken Fried Rice",   "Rice",        200.00));
        menu.add(new FoodItem(7,  "Masala Dosa",          "South Indian",100.00));
        menu.add(new FoodItem(8,  "Idli Sambar (3 pcs)",  "South Indian", 80.00));
        menu.add(new FoodItem(9,  "Veg Noodles",          "Noodles",     140.00));
        menu.add(new FoodItem(10, "Chicken Noodles",      "Noodles",     190.00));
        menu.add(new FoodItem(11, "Cold Coffee",          "Drinks",       80.00));
        menu.add(new FoodItem(12, "Fresh Lime Soda",      "Drinks",       60.00));
        menu.add(new FoodItem(13, "Chocolate Brownie",    "Desserts",    110.00));
        menu.add(new FoodItem(14, "Vanilla Ice Cream",    "Desserts",     90.00));
        menu.add(new FoodItem(15, "Butter Naan (2 pcs)",  "Indian",       70.00));
    }

    // ── Menu ─────────────────────────────────────────────────────────────────
    public List<FoodItem> getMenu() {
        return menu;
    }

    public FoodItem findItemById(int id) {
        for (FoodItem item : menu) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    // ── Cart ─────────────────────────────────────────────────────────────────
    public List<CartItem> getCart() {
        return cart;
    }

    /**
     * Add an item to the cart.
     * If the item already exists in the cart, just increase its quantity.
     * Returns false if the item ID does not exist.
     */
    public boolean addToCart(int itemId, int quantity) {
        FoodItem item = findItemById(itemId);
        if (item == null) return false;

        // Check if already in cart
        for (CartItem ci : cart) {
            if (ci.getFoodItem().getId() == itemId) {
                ci.addQuantity(quantity);
                return true;
            }
        }
        cart.add(new CartItem(item, quantity));
        return true;
    }

    public void clearCart() {
        cart.clear();
    }

    // ── Bill calculation ─────────────────────────────────────────────────────
    /** Raw total before discount & GST */
    public double calcRawTotal() {
        double total = 0;
        for (CartItem ci : cart) total += ci.getSubtotal();
        return total;
    }

    /** 10 % discount if raw total > 1000 */
    public double calcDiscount(double rawTotal) {
        return (rawTotal > 1000) ? rawTotal * 0.10 : 0;
    }

    /** 5 % GST on (rawTotal - discount) */
    public double calcGST(double afterDiscount) {
        return afterDiscount * 0.05;
    }

    /** Final payable amount */
    public double calcFinalTotal(double rawTotal, double discount, double gst) {
        return rawTotal - discount + gst;
    }

    // ── Order / Sales ─────────────────────────────────────────────────────────
    /**
     * Finalise the current cart:
     *   - generates a new order ID
     *   - stores each cart item as a Sale record
     *   - clears the cart
     *   - returns the generated order ID
     */
    public int generateOrder() {
        int orderId = nextOrderId++;
        double rawTotal   = calcRawTotal();
        double discount   = calcDiscount(rawTotal);
        double afterDisc  = rawTotal - discount;
        double gst        = calcGST(afterDisc);
        double finalTotal = calcFinalTotal(rawTotal, discount, gst);

        for (CartItem ci : cart) {
            Sale sale = new Sale(
                orderId,
                ci.getFoodItem().getName(),
                ci.getQuantity(),
                ci.getFoodItem().getPrice(),
                ci.getSubtotal()
            );
            salesLog.add(sale);
        }

        clearCart();
        return orderId;
    }

    public List<Sale> getSalesLog() {
        return salesLog;
    }

    /** Total number of unique orders */
    public int getTotalOrders() {
        // Count distinct order IDs
        List<Integer> seen = new ArrayList<>();
        for (Sale s : salesLog) {
            if (!seen.contains(s.getOrderId())) seen.add(s.getOrderId());
        }
        return seen.size();
    }

    /** Sum of all sale totals (item subtotals, before discount/GST) */
    public double getTotalRevenue() {
        double rev = 0;
        for (Sale s : salesLog) rev += s.getTotalAmount();
        return rev;
    }
}
