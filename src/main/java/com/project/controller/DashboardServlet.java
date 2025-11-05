package com.project.controller;

import com.google.gson.Gson;
import com.project.dao.*;
import com.project.model.User;
import com.project.model.Stock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class DashboardServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
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
            User user = userDAO.getUserById(userId);

            if (user == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "User not found");
                resp.getWriter().write(gson.toJson(response));
                return;
            }

            Map<Integer, Integer> portfolio = portfolioDAO.getUserPortfolio(userId);
            List<Map<String, Object>> holdings = new ArrayList<>();
            double totalPortfolioValue = 0.0;
            double totalCost = 0.0;

            for (Map.Entry<Integer, Integer> entry : portfolio.entrySet()) {
                Stock stock = stockDAO.getStockById(entry.getKey());
                if (stock != null) {
                    int quantity = entry.getValue();
                    double purchasePrice = portfolioDAO.getPurchasePrice(userId, entry.getKey());
                    double currentValue = stock.getPrice() * quantity;
                    double cost = purchasePrice * quantity;
                    double gainLoss = currentValue - cost;
                    double gainLossPercent = (gainLoss / cost) * 100;

                    Map<String, Object> holding = new HashMap<>();
                    holding.put("symbol", stock.getSymbol());
                    holding.put("companyName", stock.getCompanyName());
                    holding.put("quantity", quantity);
                    holding.put("currentPrice", stock.getPrice());
                    holding.put("purchasePrice", purchasePrice);
                    holding.put("currentValue", currentValue);
                    holding.put("gainLoss", gainLoss);
                    holding.put("gainLossPercent", gainLossPercent);

                    holdings.add(holding);
                    totalPortfolioValue += currentValue;
                    totalCost += cost;
                }
            }

            double totalGainLoss = totalPortfolioValue - totalCost;
            double totalGainLossPercent = totalCost > 0 ? (totalGainLoss / totalCost) * 100 : 0;

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("username", user.getUsername());
            response.put("userType", user.getUserType());
            response.put("balance", user.getBalance());
            response.put("portfolioValue", totalPortfolioValue);
            response.put("totalValue", user.getBalance() + totalPortfolioValue);
            response.put("totalGainLoss", totalGainLoss);
            response.put("totalGainLossPercent", totalGainLossPercent);
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
