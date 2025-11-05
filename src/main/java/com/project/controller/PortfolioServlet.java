package com.project.controller;

import com.google.gson.Gson;
import com.project.dao.*;
import com.project.model.Stock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class PortfolioServlet extends HttpServlet {
    private PortfolioDAO portfolioDAO = new PortfolioDAO();
    private StockDAO stockDAO = new StockDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Not logged in");
            resp.getWriter().write(gson.toJson(response));
            return;
        }

        try {
            int userId = (Integer) session.getAttribute("userId");
            Map<Integer, Integer> portfolio = portfolioDAO.getUserPortfolio(userId);
            
            List<Map<String, Object>> holdings = new ArrayList<>();
            
            for (Map.Entry<Integer, Integer> entry : portfolio.entrySet()) {
                Stock stock = stockDAO.getStockById(entry.getKey());
                if (stock != null) {
                    Map<String, Object> holding = new HashMap<>();
                    holding.put("symbol", stock.getSymbol());
                    holding.put("companyName", stock.getCompanyName());
                    holding.put("quantity", entry.getValue());
                    holding.put("price", stock.getPrice());
                    holding.put("value", stock.getPrice() * entry.getValue());
                    holdings.add(holding);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("holdings", holdings);

            resp.getWriter().write(gson.toJson(response));
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            resp.getWriter().write(gson.toJson(response));
        }
    }
}
