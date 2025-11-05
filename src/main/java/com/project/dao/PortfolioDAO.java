package com.project.dao;

import com.project.model.Stock;
import com.project.utils.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PortfolioDAO {
    
    public Map<Integer, Integer> getUserPortfolio(int userId) {
        Map<Integer, Integer> portfolio = new HashMap<>();
        String sql = "SELECT stock_id, quantity FROM portfolio WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                portfolio.put(rs.getInt("stock_id"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return portfolio;
    }
    
    public boolean addOrUpdateHolding(int userId, int stockId, int quantity) {
        String checkSql = "SELECT quantity FROM portfolio WHERE user_id = ? AND stock_id = ?";
        String updateSql = "UPDATE portfolio SET quantity = ? WHERE user_id = ? AND stock_id = ?";
        String insertSql = "INSERT INTO portfolio (user_id, stock_id, quantity, purchase_price) " +
                          "VALUES (?, ?, ?, (SELECT price FROM stocks WHERE stock_id = ?))";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, stockId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                int newQuantity = currentQuantity + quantity;
                
                if (newQuantity <= 0) {
                    return removeHolding(userId, stockId);
                }
                
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, stockId);
                return updateStmt.executeUpdate() > 0;
            } else {
                if (quantity <= 0) return false;
                
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, stockId);
                insertStmt.setInt(3, quantity);
                insertStmt.setInt(4, stockId);
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean removeHolding(int userId, int stockId) {
        String sql = "DELETE FROM portfolio WHERE user_id = ? AND stock_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, stockId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public double getPurchasePrice(int userId, int stockId) {
        String sql = "SELECT purchase_price FROM portfolio WHERE user_id = ? AND stock_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, stockId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("purchase_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0.0;
    }
}
