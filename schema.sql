-- Drop tables if they exist
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS portfolio CASCADE;
DROP TABLE IF EXISTS stocks CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Create users table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) DEFAULT 'REGULAR',
    balance DECIMAL(15, 2) DEFAULT 10000.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create stocks table
CREATE TABLE stocks (
    stock_id SERIAL PRIMARY KEY,
    symbol VARCHAR(10) UNIQUE NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    change_percent DECIMAL(5, 2) DEFAULT 0.00,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create portfolio table
CREATE TABLE portfolio (
    portfolio_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    stock_id INTEGER NOT NULL REFERENCES stocks(stock_id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, stock_id)
);

-- Create transactions table
CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    stock_symbol VARCHAR(10) NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    type VARCHAR(10) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample users
INSERT INTO users (username, password, user_type, balance) VALUES
('john_doe', 'password123', 'REGULAR', 10000.00),
('jane_smith', 'password456', 'PREMIUM', 25000.00),
('demo', 'demo', 'REGULAR', 50000.00);

-- Insert sample stocks
INSERT INTO stocks (symbol, company_name, price, change_percent) VALUES
('AAPL', 'Apple Inc.', 178.25, 1.25),
('GOOGL', 'Alphabet Inc.', 142.80, -0.45),
('MSFT', 'Microsoft Corporation', 378.50, 2.10),
('AMZN', 'Amazon.com Inc.', 145.30, 0.85),
('TSLA', 'Tesla Inc.', 242.15, -1.60),
('META', 'Meta Platforms Inc.', 485.20, 3.25),
('NVDA', 'NVIDIA Corporation', 495.75, 4.50),
('NFLX', 'Netflix Inc.', 425.60, -0.30);

-- Insert sample portfolio holdings
INSERT INTO portfolio (user_id, stock_id, quantity, purchase_price) VALUES
(1, 1, 10, 170.00),
(1, 3, 5, 350.00),
(2, 2, 15, 135.00),
(2, 4, 8, 140.00),
(2, 7, 12, 450.00),
(3, 1, 20, 175.00),
(3, 5, 10, 250.00);

-- Insert sample transactions
INSERT INTO transactions (user_id, stock_symbol, quantity, price, type) VALUES
(1, 'AAPL', 10, 170.00, 'BUY'),
(1, 'MSFT', 5, 350.00, 'BUY'),
(2, 'GOOGL', 15, 135.00, 'BUY'),
(2, 'AMZN', 8, 140.00, 'BUY'),
(2, 'NVDA', 12, 450.00, 'BUY');
