package com.project.controller;

import com.google.gson.Gson;
import com.project.dao.StockDAO;
import com.project.model.Stock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class MarketServlet extends HttpServlet {
    private StockDAO stockDAO = new StockDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Stock> stocks = stockDAO.getAllStocks();
            
            List<Map<String, Object>> stockList = new ArrayList<>();
            for (Stock stock : stocks) {
                Map<String, Object> stockData = new HashMap<>();
                stockData.put("stockId", stock.getStockId());
                stockData.put("symbol", stock.getSymbol());
                stockData.put("companyName", stock.getCompanyName());
                stockData.put("price", stock.getPrice());
                stockData.put("changePercent", stock.getChangePercent());
                stockList.add(stockData);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("stocks", stockList);

            resp.getWriter().write(gson.toJson(response));
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            resp.getWriter().write(gson.toJson(response));
        }
    }
}
