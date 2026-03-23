// CartItem.java
// Represents a food item added to the customer's cart with a quantity

public class CartItem {
    private FoodItem foodItem;
    private int quantity;

    public CartItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    // Getters
    public FoodItem getFoodItem() { return foodItem; }
    public int getQuantity() { return quantity; }

    // Calculate total price for this cart item
    public double getSubtotal() {
        return foodItem.getPrice() * quantity;
    }

    // Increase quantity (if same item added again)
    public void addQuantity(int qty) {
        this.quantity += qty;
    }

    @Override
    public String toString() {
        return foodItem.getName() + " x" + quantity + " = Rs." + getSubtotal();
    }
}
