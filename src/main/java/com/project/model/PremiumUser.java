package com.project.model;

public class PremiumUser extends User {
    private static final double BONUS_PERCENTAGE = 0.02;
    
    public PremiumUser() {
        super();
        this.userType = "PREMIUM";
    }
    
    public PremiumUser(int userId, String username, String password, double balance) {
        super(userId, username, password, balance);
        this.userType = "PREMIUM";
    }
    
    @Override
    public double getPortfolioValue() {
        double baseValue = portfolio.calculateTotalValue();
        return baseValue * (1 + BONUS_PERCENTAGE);
    }
}
