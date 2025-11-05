package com.project.model;

import java.util.Date;

public abstract class TransactionBase {
    protected int transactionId;
    protected int userId;
    protected String stockSymbol;
    protected int quantity;
    protected double price;
    protected String type;
    protected Date date;
    
    public TransactionBase() {
        this.date = new Date();
    }
    
    public TransactionBase(int transactionId, int userId, String stockSymbol, 
                          int quantity, double price, String type) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.date = new Date();
    }
    
    public abstract boolean execute(User user, Stock stock);
    
    public int getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getStockSymbol() {
        return stockSymbol;
    }
    
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public double getTotalAmount() {
        return price * quantity;
    }
}
