# Stock Portfolio Management System

## Overview

A fully functional Java-based web application for managing stock portfolios with real-time market simulation. Built as an educational project for a team of 5 members demonstrating all major Object-Oriented Programming principles including inheritance, polymorphism, encapsulation, abstraction, and interface implementation. The system features PostgreSQL database integration, Jetty embedded servlet container, and a Robinhood-inspired web UI.

**Status**: ✅ Application running on port 5000. Demo account: username `demo`, password `demo`.

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Core Domain Model (OOP-Driven)

**Stock Market Simulation** (Member A's Contribution)
- Uses `PriceChangeable` interface to define contracts for price updates across different financial instruments
- `Stock` class implements the interface to handle dynamic price changes
- Stock class includes proper equals() and hashCode() implementation based on stockId for HashMap compatibility
- `Market` class maintains collection of stocks and coordinates price update operations
- Demonstrates polymorphism through interface implementation and encapsulation through private field access

**User Management & Inheritance Hierarchy** (Member B's Contribution)
- Abstract `User` base class defines common user behavior and attributes with abstract getPortfolioValue() method
- `RegularUser` and `PremiumUser` extend the base class with different implementations
- Premium users receive 2% bonus on portfolio valuation (polymorphic behavior through method overriding)
- Composition pattern: Each User contains a Portfolio object (has-a relationship)
- `Portfolio` class uses HashMap to aggregate stock holdings and provides value calculations

**Transaction System** (Member C's Contribution)
- Abstract `TransactionBase` class defines structure with abstract execute() method
- Concrete `Transaction` class handles buy/sell operations with full encapsulation
- `TransactionManager` maintains transaction history using Java Collections (ArrayList/List)
- Provides abstraction layer for transaction logging and retrieval
- Transaction.execute() validates user balance and portfolio holdings before executing trades

**Data Persistence Layer** (Member D's Contribution)
- DAO (Data Access Object) pattern abstracts database operations
- `DatabaseConnection` utility manages PostgreSQL connections via environment variables
- Individual DAO classes (`UserDAO`, `StockDAO`, `TransactionDAO`, `PortfolioDAO`) handle CRUD operations
- Uses JDBC PreparedStatements for SQL execution with proper exception handling
- Try-with-resources ensures proper connection management
- Separation of concerns: business logic remains independent of database implementation

### Frontend Architecture

**Multi-Page Application (MPA)**
- Static HTML pages: Login/Register (`index.html`), Dashboard (`dashboard.html`), Market (`market.html`), Transactions (`transactions.html`)
- Modular JavaScript files per page functionality
- CSS uses CSS custom properties for theming and consistent styling
- Client-side form validation and dynamic UI updates via DOM manipulation

**Client-Server Communication**
- RESTful API endpoints for all data operations (`/api/login`, `/api/dashboard`, `/api/market`, etc.)
- Asynchronous Fetch API calls for non-blocking server communication
- JSON data format for request/response payloads
- Session-based authentication with server-side session management

### Backend Architecture

**Servlet-Based Request Handling** (Member E's Contribution)
- Java Servlets handle HTTP requests and route to appropriate business logic
- Servlets include: `LoginServlet`, `RegisterServlet`, `BuyServlet`, `SellServlet`, `DashboardServlet`, `MarketServlet`, `PortfolioServlet`, `TransactionsServlet`, `LogoutServlet`
- Leverages servlet inheritance from HttpServlet for common functionality
- Session management for user authentication state
- Response formatting as JSON using Gson library for API endpoints
- Main.java configures embedded Jetty server on port 5000

**Business Logic Layer**
- Domain model classes encapsulate business rules (stock pricing, portfolio calculations, transaction validation)
- Service layer coordinates between servlets and DAOs
- Validation logic for trading operations (sufficient funds, valid quantities)
- Calculation engines for portfolio metrics, gains/losses, and percentage returns

## External Dependencies

**Database**
- PostgreSQL for persistent data storage
- Schema includes tables for: users, stocks, portfolios, holdings, transactions
- JDBC driver for database connectivity
- Connection pooling for performance optimization

**Web Server**
- Embedded Jetty 9.4 servlet container for deploying web application
- Handles HTTP request/response lifecycle and servlet management
- Configured to serve static files from web/ directory
- Running on 0.0.0.0:5000

**Frontend Libraries**
- Vanilla JavaScript (no frameworks) for client-side interactivity
- Modern browser APIs: Fetch, DOM manipulation, LocalStorage potential for caching

**Development Tools**
- Java 11 with GraalVM
- Maven 3.x for dependency management and build automation
- JDBC PostgreSQL driver 42.6.0
- Gson 2.10.1 for JSON processing
- Embedded Jetty 9.4.51 for servlet container
- HTML5/CSS3 standards compliance

## Recent Changes (November 5, 2025)

- ✅ Complete implementation of all 5 team member contributions
- ✅ Database schema created with sample data (3 users, 8 stocks, sample portfolios)
- ✅ All servlets implemented and tested
- ✅ Web UI completed with Robinhood-inspired design (#00C805 green, #FF3B30 red, #007AFF blue)
- ✅ Fixed critical bug: Added equals() and hashCode() to Stock class for HashMap compatibility
- ✅ Application deployed and running on port 5000

## Running the Application

1. **Start the application**: The workflow is already configured. The app runs automatically.
2. **Access the UI**: Navigate to http://0.0.0.0:5000
3. **Login**: Use demo account (username: `demo`, password: `demo`)
4. **Features**:
   - View portfolio dashboard with real-time values
   - Browse stock market
   - Buy and sell stocks
   - View transaction history

## Team Member Contributions

All team members have successfully implemented their OOP components:
- **Member A**: Interface-based polymorphism (PriceChangeable, Stock, Market)
- **Member B**: Inheritance & composition (User hierarchy, Portfolio)
- **Member C**: Abstraction (TransactionBase, Transaction, TransactionManager)
- **Member D**: DAO pattern & JDBC (DatabaseConnection, all DAO classes)
- **Member E**: Servlet MVC (All servlets, embedded Jetty configuration)