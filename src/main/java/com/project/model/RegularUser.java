package com.project.model;

public class RegularUser extends User {
    
    public RegularUser() {
        super();
        this.userType = "REGULAR";
    }
    
    public RegularUser(int userId, String username, String password, double balance) {
        super(userId, username, password, balance);
        this.userType = "REGULAR";
    }
    
    @Override
    public double getPortfolioValue() {
        return portfolio.calculateTotalValue();
    }
}
