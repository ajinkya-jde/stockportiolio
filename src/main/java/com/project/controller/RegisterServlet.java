package com.project.controller;

import com.google.gson.Gson;
import com.project.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String userType = req.getParameter("userType");
            double balance = 10000.00;

            if (userType == null || userType.isEmpty()) {
                userType = "REGULAR";
            }

            boolean success = userDAO.addUser(username, password, userType, balance);

            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "Registration successful");
            } else {
                response.put("success", false);
                response.put("message", "Registration failed. Username may already exist.");
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
