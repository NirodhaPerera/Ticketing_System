Real-Time Ticketing System

A full-stack real-time ticketing platform built with Angular for the frontend (ticket-Sys-Ui) and Spring Boot for the backend (ticketing_System). This system follows the Producer-Consumer pattern using Java multithreading with the Runnable interface, and integrates a MySQL database for persistent data storage.

🔧 Technologies Used
Frontend: Angular (ticket-Sys-Ui)

Backend: Spring Boot (ticketing_System)

Database: MySQL

Multithreading: Java Runnable interface (for Producers and Consumers)

💡 Core Features
Vendors (Producers) release tickets

Customers (Consumers) purchase tickets

Real-time handling using threads

Smooth coordination using synchronized logic

MySQL integration for user, ticket, and transaction data


-------------------------------------------------------------------------------------------------------------------------------------------------------

Ticketing_System_CW

This is a terminal-based Java project that demonstrates the Producer-Consumer pattern using multithreading with the Runnable interface.

🧵 How It Works
Vendors act as producers, releasing tickets.

Customers act as consumers, purchasing tickets.

Threads are created by implementing the Runnable interface.

Proper synchronization is used to handle concurrent access and ensure thread-safe operations.

🎯 Purpose
This project was built to understand and practice core Java multithreading concepts, especially the Producer-Consumer model using the Runnable interface.
