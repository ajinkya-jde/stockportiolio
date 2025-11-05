# Stock Portfolio Management System

A comprehensive web-based stock portfolio management application built with Java, demonstrating Object-Oriented Programming principles.

## 🎯 Project Overview

This application allows users to track and manage their stock investments with real-time portfolio analytics. Built as an educational OOP project for a team of 5 members over 1.5 days.

## 🏗️ Architecture & OOP Concepts

### Member A - Market Logic (Interface & Polymorphism)
- **Interface**: `PriceChangeable` - Defines contract for price updates
- **Polymorphism**: Stock class implements PriceChangeable interface
- **Encapsulation**: Stock and Market classes with private fields

**Files**: 
- `model/interfaces/PriceChangeable.java`
- `model/Stock.java`
- `model/Market.java`

### Member B - User & Portfolio (Inheritance & Composition)
- **Abstract Class**: `User` - Base class for user types
- **Inheritance**: `RegularUser` and `PremiumUser` extend User
- **Composition**: User has-a Portfolio relationship
- **Polymorphism**: Override `getPortfolioValue()` (Premium users get 2% bonus)

**Files**:
- `model/User.java`
- `model/RegularUser.java`
- `model/PremiumUser.java`
- `model/Portfolio.java`

### Member C - Transactions (Abstraction)
- **Abstract Class**: `TransactionBase` - Defines transaction structure
- **Encapsulation**: Transaction class with private fields
- **Collections**: TransactionManager maintains transaction list

**Files**:
- `model/TransactionBase.java`
- `model/Transaction.java`
- `model/TransactionManager.java`

### Member D - Database & DAO (Abstraction & JDBC)
- **DAO Pattern**: Data Access Object abstraction
- **JDBC**: PostgreSQL connection and CRUD operations
- **Exception Handling**: Proper database error management

**Files**:
- `utils/DatabaseConnection.java`
- `dao/UserDAO.java`
- `dao/StockDAO.java`
- `dao/TransactionDAO.java`
- `dao/PortfolioDAO.java`

### Member E - Web UI & Servlets (MVC Pattern)
- **Servlets**: HTTP request handlers
- **MVC**: Model-View-Controller architecture
- **Session Management**: User authentication and sessions

**Files**:
- `controller/LoginServlet.java`
- `controller/DashboardServlet.java`
- `controller/BuyServlet.java`
- `controller/SellServlet.java`
- `controller/MarketServlet.java`
- `controller/PortfolioServlet.java`
- `controller/TransactionsServlet.java`

## 🎨 Design Features

**Color Scheme** (Robinhood-inspired):
- Primary Green: #00C805 (gains)
- Primary Red: #FF3B30 (losses)
- Accent Blue: #007AFF
- Background: #FFFFFF
- Text: #1D1D1F

**Features**:
- Clean, modern card-based layout
- Real-time portfolio value calculation
- Color-coded gains/losses
- Responsive mobile-first design
- Interactive stock trading interface

## 🚀 Setup & Running

### Prerequisites
- Java 11 or higher
- Maven
- PostgreSQL database

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd stock-portfolio-manager
```

2. **Set up database**
The database is automatically configured via environment variables.

3. **Install dependencies**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn exec:java
```

5. **Access the application**
Open browser to: `http://localhost:5000`

### Demo Account
- Username: `demo`
- Password: `demo`

## 📊 Database Schema

**Tables**:
- `users` - User accounts (Regular/Premium)
- `stocks` - Available stocks with prices
- `portfolio` - User stock holdings
- `transactions` - Buy/sell transaction history

## 🎓 OOP Concepts Demonstrated

| Concept | Implementation | Location |
|---------|---------------|----------|
| **Encapsulation** | Private fields with getters/setters | All model classes |
| **Inheritance** | RegularUser & PremiumUser extend User | User hierarchy |
| **Polymorphism** | PriceChangeable interface, overridden methods | Stock, User classes |
| **Abstraction** | Abstract User, TransactionBase classes | User.java, TransactionBase.java |
| **Composition** | User has-a Portfolio | User.java |
| **Interfaces** | PriceChangeable for stocks | PriceChangeable.java |
| **Collections** | ArrayList, HashMap, Map usage | Throughout |
| **Exception Handling** | Database and transaction errors | DAO classes |

## 📁 Project Structure

```
stock-portfolio-manager/
├── src/com/project/
│   ├── Main.java                    # Application entry point
│   ├── model/                       # Business logic layer
│   │   ├── interfaces/
│   │   │   └── PriceChangeable.java
│   │   ├── Stock.java
│   │   ├── Market.java
│   │   ├── User.java
│   │   ├── RegularUser.java
│   │   ├── PremiumUser.java
│   │   ├── Portfolio.java
│   │   ├── Transaction.java
│   │   ├── TransactionBase.java
│   │   └── TransactionManager.java
│   ├── dao/                         # Data access layer
│   │   ├── UserDAO.java
│   │   ├── StockDAO.java
│   │   ├── TransactionDAO.java
│   │   └── PortfolioDAO.java
│   ├── controller/                  # Servlet controllers
│   │   ├── LoginServlet.java
│   │   ├── RegisterServlet.java
│   │   ├── DashboardServlet.java
│   │   ├── MarketServlet.java
│   │   ├── BuyServlet.java
│   │   ├── SellServlet.java
│   │   ├── PortfolioServlet.java
│   │   ├── TransactionsServlet.java
│   │   └── LogoutServlet.java
│   └── utils/
│       └── DatabaseConnection.java
├── web/                             # Frontend
│   ├── index.html                   # Login page
│   ├── dashboard.html               # Portfolio dashboard
│   ├── market.html                  # Stock market
│   ├── transactions.html            # Transaction history
│   └── assets/
│       ├── css/style.css
│       └── js/
│           ├── auth.js
│           ├── dashboard.js
│           ├── market.js
│           └── transactions.js
├── schema.sql                       # Database schema
├── pom.xml                          # Maven configuration
└── README.md

## 🔧 Technologies Used

**Backend**:
- Java 11
- Jakarta Servlets
- JDBC (PostgreSQL Driver)
- Jetty (Embedded Server)
- Gson (JSON Processing)

**Frontend**:
- HTML5/CSS3
- Vanilla JavaScript
- Responsive Design

**Database**:
- PostgreSQL

## 🎤 Viva Preparation

### Key Questions by Team Member

**Member A (Market Logic)**:
- Q: What is an interface?
- A: A contract that defines methods a class must implement. `PriceChangeable` defines `updatePrice()` method.

**Member B (Portfolio)**:
- Q: Explain inheritance and composition.
- A: Inheritance: RegularUser/PremiumUser extend User. Composition: User has-a Portfolio.

**Member C (Transactions)**:
- Q: Why use abstract class?
- A: TransactionBase provides common structure while allowing subclasses to implement `execute()` method.

**Member D (Database)**:
- Q: How does JDBC work?
- A: JDBC connects Java to database using DriverManager, PreparedStatement prevents SQL injection.

**Member E (Servlets)**:
- Q: Explain request-response flow.
- A: Client → Servlet (process request) → DAO (database) → Model → JSON response → Client

## 📈 Features

✅ User authentication (Login/Register)  
✅ Portfolio dashboard with real-time value  
✅ Stock market with live prices  
✅ Buy/Sell stock functionality  
✅ Transaction history tracking  
✅ Gain/Loss calculations  
✅ Premium user benefits (2% bonus)  
✅ Responsive mobile design  

## 🎯 Learning Outcomes

- Understanding of OOP principles in real-world applications
- JDBC database connectivity and operations
- Servlet-based web architecture
- MVC design pattern implementation
- RESTful API design
- Session management and authentication

## 👥 Team Collaboration

This project is designed for 5 team members, each contributing specific OOP components:
1. Market Logic with Interfaces
2. User Management with Inheritance
3. Transaction System with Abstraction
4. Database Layer with DAO Pattern
5. Web Integration with Servlets

All components integrate seamlessly to create a complete stock portfolio management system.

## 📝 License

Educational project for OOP learning purposes.
