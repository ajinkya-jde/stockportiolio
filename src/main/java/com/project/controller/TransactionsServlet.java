package com.project.controller;

import com.google.gson.Gson;
import com.project.dao.TransactionDAO;
import com.project.model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class TransactionsServlet extends HttpServlet {
    private TransactionDAO transactionDAO = new TransactionDAO();
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
            List<Transaction> transactions = transactionDAO.getUserTransactions(userId);
            
            List<Map<String, Object>> transactionList = new ArrayList<>();
            for (Transaction transaction : transactions) {
                Map<String, Object> txn = new HashMap<>();
                txn.put("transactionId", transaction.getTransactionId());
                txn.put("stockSymbol", transaction.getStockSymbol());
                txn.put("quantity", transaction.getQuantity());
                txn.put("price", transaction.getPrice());
                txn.put("type", transaction.getType());
                txn.put("totalAmount", transaction.getTotalAmount());
                txn.put("date", transaction.getDate().toString());
                transactionList.add(txn);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("transactions", transactionList);

            resp.getWriter().write(gson.toJson(response));
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            resp.getWriter().write(gson.toJson(response));
        }
    }
}
