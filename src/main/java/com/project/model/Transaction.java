package com.project.model;

public class Transaction extends TransactionBase {
    
    public Transaction() {
        super();
    }
    
    public Transaction(int transactionId, int userId, String stockSymbol, 
                      int quantity, double price, String type) {
        super(transactionId, userId, stockSymbol, quantity, price, type);
    }
    
    @Override
    public boolean execute(User user, Stock stock) {
        double totalCost = getTotalAmount();
        
        if ("BUY".equalsIgnoreCase(type)) {
            if (user.getBalance() >= totalCost) {
                user.setBalance(user.getBalance() - totalCost);
                user.getPortfolio().addStock(stock, quantity);
                return true;
            }
            return false;
        } else if ("SELL".equalsIgnoreCase(type)) {
            if (user.getPortfolio().hasStock(stock) && 
                user.getPortfolio().getQuantity(stock) >= quantity) {
                user.setBalance(user.getBalance() + totalCost);
                user.getPortfolio().removeStock(stock, quantity);
                return true;
            }
            return false;
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%s %d shares of %s at $%.2f on %s", 
            type, quantity, stockSymbol, price, date.toString());
    }
}
