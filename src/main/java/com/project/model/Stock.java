package com.project.model;

import com.project.model.interfaces.PriceChangeable;

public class Stock implements PriceChangeable {
    private int stockId;
    private String symbol;
    private String companyName;
    private double price;
    private double changePercent;
    
    public Stock() {
    }
    
    public Stock(int stockId, String symbol, String companyName, double price) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.changePercent = 0.0;
    }
    
    @Override
    public void updatePrice(double percentChange) {
        this.changePercent = percentChange;
        this.price = this.price * (1 + percentChange / 100);
    }
    
    public int getStockId() {
        return stockId;
    }
    
    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getChangePercent() {
        return changePercent;
    }
    
    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f (%.2f%%)", 
            companyName, symbol, price, changePercent);
    }
}
