package com.project.controller;

import com.google.gson.Gson;
import com.project.dao.UserDAO;
import com.project.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
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

            User user = userDAO.validateLogin(username, password);

            Map<String, Object> response = new HashMap<>();

            if (user != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userType", user.getUserType());

                response.put("success", true);
                response.put("message", "Login successful");
                response.put("userId", user.getUserId());
                response.put("username", user.getUsername());
                response.put("userType", user.getUserType());
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password");
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
