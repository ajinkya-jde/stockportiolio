package com.project.dao;

import com.project.model.Stock;
import com.project.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM stocks ORDER BY symbol";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                stocks.add(mapResultSetToStock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stocks;
    }
    
    public Stock getStockBySymbol(String symbol) {
        String sql = "SELECT * FROM stocks WHERE symbol = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, symbol);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStock(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Stock getStockById(int stockId) {
        String sql = "SELECT * FROM stocks WHERE stock_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, stockId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStock(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean updateStockPrice(String symbol, double newPrice, double changePercent) {
        String sql = "UPDATE stocks SET price = ?, change_percent = ? WHERE symbol = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, newPrice);
            pstmt.setDouble(2, changePercent);
            pstmt.setString(3, symbol);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addStock(String symbol, String companyName, double price) {
        String sql = "INSERT INTO stocks (symbol, company_name, price, change_percent) VALUES (?, ?, ?, 0.0)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, symbol);
            pstmt.setString(2, companyName);
            pstmt.setDouble(3, price);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private Stock mapResultSetToStock(ResultSet rs) throws SQLException {
        int stockId = rs.getInt("stock_id");
        String symbol = rs.getString("symbol");
        String companyName = rs.getString("company_name");
        double price = rs.getDouble("price");
        
        Stock stock = new Stock(stockId, symbol, companyName, price);
        stock.setChangePercent(rs.getDouble("change_percent"));
        
        return stock;
    }
}
