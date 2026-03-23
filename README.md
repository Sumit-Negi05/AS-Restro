# 🍽️ AS Restro - Food Ordering System

A Java Swing-based GUI application that simulates a restaurant food ordering system with customer and admin functionalities.

---

## 🚀 Features

### 👤 Customer Panel

* View menu (15 food items)
* Add items to cart
* Real-time cart total
* Generate bill with:

  * Subtotal
  * Discount (10% if total > ₹1000)
  * GST (5%)
  * Final payable amount

### 🔐 Admin Panel

* Secure login system
* View total orders
* View total revenue
* Sales log with complete order details
* Refresh data dynamically

---

## 🛠️ Technologies Used

* Java
* Swing (GUI)
* Object-Oriented Programming (OOP)
* ArrayList (In-memory storage)

---

## 📂 Project Structure

```
ASRestro/
 ├── FoodItem.java
 ├── CartItem.java
 ├── Sale.java
 ├── FoodOrderingSystem.java
 ├── AdminFrame.java
 ├── CustomerFrame.java
 └── MainFrame.java
```

---

## ▶️ How to Run

### 1. Compile

```
javac *.java
```

### 2. Run

```
java MainFrame
```

---

## 🔑 Admin Credentials

```
Username: admin
Password: ASrestro
```

---

## 💡 Billing Rules

* 10% discount if total exceeds ₹1000
* 5% GST applied after discount
* Order ID auto-increments starting from 1001
* Cart is cleared after order generation

---

## 📸 Screenshots (Add here)

*(You can upload screenshots of your UI later)*

---

## 📌 Future Improvements

* Database integration (MySQL)
* User authentication system
* Search & filter menu
* Save sales data to file

---

## 👨‍💻 Author

Sumit Negi

---
