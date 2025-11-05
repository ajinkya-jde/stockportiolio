package com.project;

import com.project.controller.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.DefaultServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(5000);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new LoginServlet()), "/api/login");
        context.addServlet(new ServletHolder(new RegisterServlet()), "/api/register");
        context.addServlet(new ServletHolder(new DashboardServlet()), "/api/dashboard");
        context.addServlet(new ServletHolder(new MarketServlet()), "/api/market");
        context.addServlet(new ServletHolder(new BuyServlet()), "/api/buy");
        context.addServlet(new ServletHolder(new SellServlet()), "/api/sell");
        context.addServlet(new ServletHolder(new PortfolioServlet()), "/api/portfolio");
        context.addServlet(new ServletHolder(new TransactionsServlet()), "/api/transactions");
        context.addServlet(new ServletHolder(new LogoutServlet()), "/api/logout");
        
        ServletHolder staticHolder = new ServletHolder("static", DefaultServlet.class);
        staticHolder.setInitParameter("resourceBase", "./web");
        staticHolder.setInitParameter("dirAllowed", "true");
        staticHolder.setInitParameter("pathInfoOnly", "true");
        context.addServlet(staticHolder, "/*");

        System.out.println("Starting Stock Portfolio Management System on http://0.0.0.0:5000");
        server.start();
        server.join();
    }
}
