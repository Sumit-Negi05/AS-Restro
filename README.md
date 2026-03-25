<div align="center">
  <img src="logo.svg" alt="AS Restro" width="260" />
  
  <h1>🍽️ AS Restro Food Ordering System</h1>

  <p>
    <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java 17" />
    <img src="https://img.shields.io/badge/Swing-GUI-6DB33F?style=for-the-badge&logo=openjdk&logoColor=white" alt="Swing GUI" />
    <img src="https://img.shields.io/badge/OOP-Architecture-blueviolet?style=for-the-badge" alt="OOP Architecture" />
  </p>

  <p>
    <img src="https://img.shields.io/badge/Version-1.0.0-blue?style=flat-square" alt="Version 1.0.0" />
    <img src="https://img.shields.io/badge/Platform-Desktop-lightgrey?style=flat-square&logo=windows&logoColor=white" alt="Desktop Platform" />
    <img src="https://img.shields.io/badge/Status-Complete-brightgreen?style=flat-square" alt="Status Complete" />
    <img src="https://img.shields.io/badge/Author-Sumit_Negi-orange?style=flat-square" alt="Author" />
  </p>

  <br>

  <b>A complete desktop application simulating a restaurant ordering experience.</b><br>
  From browsing the menu to generating a GST-inclusive bill and managing admin sales logs.
</div>

---

## 📌 Overview

**AS Restro** is a desktop-based food ordering system built purely with Java Swing. It features a dual-panel design—a customer-facing ordering interface and a password-protected admin dashboard. The application handles real-time cart management, automatic billing (with dynamic discounts and GST calculations), and an active, in-memory sales log.

---

## ✨ Core Features

<table>
  <tr>
    <td width="50%" valign="top">
      <h3>🧑‍🍳 Customer Panel</h3>
      <ul>
        <li>Browse a dynamic menu of <strong>15 food items</strong>.</li>
        <li>Add items to the cart with live subtotal updates.</li>
        <li>Auto-apply a <strong>10% discount</strong> on orders exceeding ₹1000.</li>
        <li>Automatic <strong>GST (5%)</strong> calculation applied post-discount.</li>
        <li>Generate a fully itemized bill with an auto-incrementing Order ID (starting at 1001).</li>
        <li>Cart clears automatically upon successful order placement.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h3>🔐 Admin Panel</h3>
      <ul>
        <li>Secure login gateway requiring valid credentials.</li>
        <li>Dashboard displaying <strong>Total Orders</strong> and <strong>Total Revenue</strong>.</li>
        <li>Comprehensive, scrollable <strong>Sales Log</strong> mapping every transaction.</li>
        <li>Dynamic data refresh to fetch the latest sales without restarting the app.</li>
      </ul>
    </td>
  </tr>
</table>

---

## 🛠 Tech Stack

| Component | Technology |
| :--- | :--- |
| **Language** | <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white" alt="Java"/> |
| **GUI Framework** | <img src="https://img.shields.io/badge/Java_Swing-6DB33F?style=flat-square&logo=openjdk&logoColor=white" alt="Java Swing"/> |
| **Architecture** | <img src="https://img.shields.io/badge/OOP-Encapsulation_%7C_Abstraction-blueviolet?style=flat-square" alt="OOP"/> |
| **Data Storage** | <img src="https://img.shields.io/badge/ArrayList-In--Memory-lightgrey?style=flat-square" alt="ArrayList"/> |

---

## 📂 Project Structure

```text
ASRestro/
 ├── FoodItem.java            # Model class for menu items
 ├── CartItem.java            # Model class for cart tracking
 ├── Sale.java                # Represents a single verified sale record
 ├── FoodOrderingSystem.java  # Core logic and data management
 ├── AdminFrame.java          # Admin dashboard GUI and logic
 ├── CustomerFrame.java       # Customer ordering GUI and logic
 └── MainFrame.java           # Entry point and navigation handling

```
## 🔑 Admin Access

To access the backend dashboard and view the sales logs, use the following default credentials:

> **Username:** `admin` <br>
> **Password:** `ASrestro`

---

## 📸 Screenshots

| Customer Ordering Panel | Admin Dashboard | Generated Bill |
| :---: | :---: | :---: |
| <img src="https://via.placeholder.com/300x200.png?text=Customer+Panel" width="300" alt="Customer Panel"> | <img src="https://via.placeholder.com/300x200.png?text=Admin+Dashboard" width="300" alt="Admin Panel"> | <img src="https://via.placeholder.com/300x200.png?text=Bill+Receipt" width="300" alt="Bill"> |

---

## 📌 Future Scope

While the current version uses in-memory storage, planned updates include:
- [ ] **Database Integration:** Replacing `ArrayList` with MySQL/PostgreSQL for persistent data storage.
- [ ] **Advanced Authentication:** Multi-user roles (Manager, Cashier) with hashed passwords.
- [ ] **Dynamic Menu Management:** Allowing admins to add, edit, or remove menu items directly from the GUI.
- [ ] **Export Functionality:** Capability to export daily sales reports to CSV or PDF.

---
<div align="center">
  <p>Developed with ☕ and Code by <b>Sumit Negi</b></p>
</div>
