# Smart Campus Management System

A Java-based desktop prototype for managing campus operations — room bookings, maintenance requests, notifications, and user management — built with Java Swing and Maven.

## Overview

The Smart Campus Management System (SCMS) is a prototype application that demonstrates the use of core object-oriented principles and software design patterns in a real-world inspired scenario. It provides a simple graphical interface for students, staff, and administrators to interact with common campus services.

## Features

- **User Management** — Role-based access for Students, Staff, and Administrators
- **Room Booking** — Reserve rooms by type (lecture hall, lab, meeting room, etc.)
- **Maintenance Requests** — Submit and track maintenance issues
- **Notifications** — Receive campus event and system updates
- **Dashboard** — Unified view of bookings, rooms, and alerts
- **Custom Exception Handling** — Validation for invalid bookings, duplicate data, unauthorized actions, and missing rooms

## Tech Stack

- **Language:** Java 21
- **UI:** Java Swing + [FlatLaf 3.4](https://www.formdev.com/flatlaf/) (modern look & feel)
- **Build Tool:** Apache Maven
- **Testing:** JUnit 5 (Jupiter 5.10.2)

## Design Patterns Used

- **Creational** — `RoomFactory`, `SystemManager` (Singleton)
- **Structural** — `CampusFacade`
- **Behavioural** — `NotificationService` (Observer pattern with `CampusEventListener`)

## Project Structure

```
Smart Campus Management System/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/scms/
│   │   │   ├── SmartCampusApp.java        # Application entry point
│   │   │   ├── gui/                       # Swing UI (frames, panels, components)
│   │   │   ├── model/                     # Domain models (User, Room, Booking, etc.)
│   │   │   ├── service/                   # Business logic services
│   │   │   ├── pattern/                   # Design pattern implementations
│   │   │   └── exception/                 # Custom exceptions
│   │   └── resources/images/              # Logos and assets
│   └── test/java/com/scms/                # JUnit 5 tests
└── target/                                # Build output
```

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 21** or higher
- **Apache Maven 3.8+**

### Clone the Repository

```bash
git clone https://github.com/<your-username>/Smart-Campus-Management-System.git
cd "Smart Campus Management System"
```

### Build the Project

```bash
mvn clean package
```

### Run the Application

Using the Maven exec plugin:

```bash
mvn exec:java
```

Or run the shaded (fat) JAR:

```bash
java -jar target/Smart_Campus_Management_System-1.0-SNAPSHOT.jar
```

### Run the Tests

```bash
mvn test
```

## Usage

1. Launch the application — the **Login** window appears.
2. Log in as a Student, Staff, or Administrator.
3. Use the main dashboard to:
   - Browse and book rooms
   - Submit maintenance requests
   - View notifications
   - Manage users and rooms (Administrator only)

## Testing

JUnit 5 test suites cover the core services:

- `BookingServiceTest` — booking lifecycle and validation
- `MaintenanceServiceTest` — maintenance request handling
- `NotificationServiceTest` — observer notifications
- `ExceptionHandlingTest` — custom exception behaviour

## Author

Developed as part of the **MSc IT** coursework at Cardiff Metropolitan University.

## License

This project is released for academic and educational purposes.

