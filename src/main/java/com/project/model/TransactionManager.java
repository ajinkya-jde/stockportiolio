package com.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionManager {
    private List<Transaction> transactions;
    
    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }
    
    public void logTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    
    public List<Transaction> getUserTransactions(int userId) {
        return transactions.stream()
            .filter(t -> t.getUserId() == userId)
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    public int getTransactionCount() {
        return transactions.size();
    }
}
