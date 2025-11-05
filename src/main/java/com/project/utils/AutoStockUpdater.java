package com.project.utils;

import java.util.Timer;
import java.util.TimerTask;

public class AutoStockUpdater {
    public static void start() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Add the symbols that exist in your DB
                String[] symbols = {"AAPL", "GOOGL", "TSLA", "MSFT", "AMZN"};
                for (String s : symbols) {
                    StockUpdater.updateStockPrice(s);
                }
            }
        }, 0, 300_000); // Every 5 minutes
    }
}
