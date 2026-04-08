# 🏠 Homestay Booking Management System

![Java](https://img.shields.io/badge/Language-Java-orange.svg)
![Lab](https://img.shields.io/badge/Course-LAB211-blue.svg)
![OOP](https://img.shields.io/badge/Concept-OOP-green.svg)

## 📝 Project Overview
This **Homestay Booking Management System** is a terminal-based application developed as part of the **LAB211 (Object-Oriented Programming Lab)** course at FPT University. The project focuses on applying core OOP principles to solve real-world data management challenges.

The system allows administrators to manage homestays, customers, and booking transactions efficiently through a structured console interface.

## ✨ Key Features
The application implements the following core functionalities:
* **Homestay Management:** Create, update, and display homestay details.
* **Customer Management:** Maintain records of customers and their contact information.
* **Booking System:**
    * Generate new booking reservations.
    * Display and track current booking lists.
* **Search & Filter:** Fast data retrieval with specialized search and sorting algorithms.
* **Persistence:** Full File I/O support to Save/Load data, ensuring information is preserved between sessions.

## 🛠 Technical Implementation
This project serves as a showcase of fundamental software engineering concepts:
* **Object-Oriented Programming (OOP):** Deep application of **Encapsulation, Inheritance, Polymorphism, and Abstraction**.
* **Java Collection Framework:** Optimized use of `ArrayList` and `HashMap` for dynamic data handling.
* **Input Validation:** Robust error handling and custom validation logic to ensure data integrity and prevent system crashes.
* **Layered Architecture:** Clear separation between Data Models, Business Logic (Services), and the User Interface (View).

## 📂 Project Structure
```text
HomestayBooking/
├── src/
│   ├── com.models/      # Entity classes (Homestay, Customer, Booking)
│   ├── com.services/    # Business logic and management services
│   ├── com.tools/       # Utility classes for validation and data entry
│   └── com.view/        # Main entry point and Menu interface
├── docs/                # Project documentation
│   └── REPORT_HomestayBooking.pdf
└── README.md
