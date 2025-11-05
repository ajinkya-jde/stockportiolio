package com.project.controller;

import com.google.gson.Gson;
import com.project.dao.*;
import com.project.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SellServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private StockDAO stockDAO = new StockDAO();
    private PortfolioDAO portfolioDAO = new PortfolioDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
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
            String symbol = req.getParameter("symbol");
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            User user = userDAO.getUserById(userId);
            Stock stock = stockDAO.getStockBySymbol(symbol);

            if (stock == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Stock not found");
                resp.getWriter().write(gson.toJson(response));
                return;
            }

            Map<Integer, Integer> portfolio = portfolioDAO.getUserPortfolio(userId);
            for (Map.Entry<Integer, Integer> entry : portfolio.entrySet()) {
                Stock s = stockDAO.getStockById(entry.getKey());
                if (s != null) {
                    user.getPortfolio().addStock(s, entry.getValue());
                }
            }

            Transaction transaction = new Transaction(0, userId, symbol, quantity, stock.getPrice(), "SELL");
            
            boolean executed = transaction.execute(user, stock);

            Map<String, Object> response = new HashMap<>();
            if (executed) {
                userDAO.updateBalance(userId, user.getBalance());
                portfolioDAO.addOrUpdateHolding(userId, stock.getStockId(), -quantity);
                transactionDAO.logTransaction(transaction);

                response.put("success", true);
                response.put("message", "Stock sold successfully");
                response.put("newBalance", user.getBalance());
            } else {
                response.put("success", false);
                response.put("message", "Insufficient shares to sell");
            }

            resp.getWriter().write(gson.toJson(response));
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            resp.getWriter().write(gson.toJson(response));
        }
    }
}
