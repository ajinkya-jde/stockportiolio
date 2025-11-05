package com.project.dao;

import com.project.model.Transaction;
import com.project.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    
    public boolean logTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, stock_symbol, quantity, price, type) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, transaction.getUserId());
            pstmt.setString(2, transaction.getStockSymbol());
            pstmt.setInt(3, transaction.getQuantity());
            pstmt.setDouble(4, transaction.getPrice());
            pstmt.setString(5, transaction.getType());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Transaction> getUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return transactions;
    }
    
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return transactions;
    }
    
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        int transactionId = rs.getInt("transaction_id");
        int userId = rs.getInt("user_id");
        String stockSymbol = rs.getString("stock_symbol");
        int quantity = rs.getInt("quantity");
        double price = rs.getDouble("price");
        String type = rs.getString("type");
        
        Transaction transaction = new Transaction(transactionId, userId, stockSymbol, quantity, price, type);
        transaction.setDate(rs.getTimestamp("date"));
        
        return transaction;
    }
}
