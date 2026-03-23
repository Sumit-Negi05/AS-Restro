// Sale.java
// Represents a single sale record stored after bill generation

public class Sale {
    private int orderId;
    private String itemName;
    private int quantity;
    private double pricePerItem;
    private double totalAmount;

    public Sale(int orderId, String itemName, int quantity, double pricePerItem, double totalAmount) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalAmount = totalAmount;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public double getPricePerItem() { return pricePerItem; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return "Order#" + orderId + " | " + itemName + " x" + quantity
                + " @ Rs." + pricePerItem + " = Rs." + totalAmount;
    }
}
