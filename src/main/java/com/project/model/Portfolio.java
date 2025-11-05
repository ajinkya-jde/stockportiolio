package com.project.model;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<Stock, Integer> holdings;
    
    public Portfolio() {
        this.holdings = new HashMap<>();
    }
    
    public void addStock(Stock stock, int quantity) {
        if (holdings.containsKey(stock)) {
            holdings.put(stock, holdings.get(stock) + quantity);
        } else {
            holdings.put(stock, quantity);
        }
    }
    
    public void removeStock(Stock stock, int quantity) {
        if (holdings.containsKey(stock)) {
            int currentQuantity = holdings.get(stock);
            if (currentQuantity <= quantity) {
                holdings.remove(stock);
            } else {
                holdings.put(stock, currentQuantity - quantity);
            }
        }
    }
    
    public double calculateTotalValue() {
        double totalValue = 0.0;
        for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
            totalValue += entry.getKey().getPrice() * entry.getValue();
        }
        return totalValue;
    }
    
    public Map<Stock, Integer> getHoldings() {
        return new HashMap<>(holdings);
    }
    
    public int getQuantity(Stock stock) {
        return holdings.getOrDefault(stock, 0);
    }
    
    public boolean hasStock(Stock stock) {
        return holdings.containsKey(stock);
    }
}
