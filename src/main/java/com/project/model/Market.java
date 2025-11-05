package com.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Market {
    private List<Stock> stocks;
    private Random random;
    
    public Market() {
        this.stocks = new ArrayList<>();
        this.random = new Random();
    }
    
    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }
    
    public Stock getStockBySymbol(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }
    
    public List<Stock> getAllStocks() {
        return new ArrayList<>(stocks);
    }
    
    public void updateMarketPrices() {
        for (Stock stock : stocks) {
            double percentChange = (random.nextDouble() - 0.5) * 10;
            stock.updatePrice(percentChange);
        }
    }
    
    public int getStockCount() {
        return stocks.size();
    }
}
