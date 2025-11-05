package com.project.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.json.JSONObject;

public class StockUpdater {

    private static final String API_KEY = System.getenv("ALPHAVANTAGE_API_KEY");

    public static void updateStockPrice(String symbol) {
        try {
            String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
                    + symbol + "&apikey=" + API_KEY;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();

            JSONObject json = new JSONObject(response.toString());
            if (!json.has("Global Quote")) return; // No data

            JSONObject quote = json.getJSONObject("Global Quote");
            double price = quote.getDouble("05. price");
            String percentStr = quote.optString("10. change percent", "0").replace("%", "");
            double changePercent = percentStr.isEmpty() ? 0 : Double.parseDouble(percentStr);

            // Update PostgreSQL
            Connection db = DatabaseConnection.getConnection();
            PreparedStatement ps = db.prepareStatement(
                "UPDATE stocks SET price=?, change_percent=?, updated_at=NOW() WHERE symbol=?"
            );
            ps.setDouble(1, price);
            ps.setDouble(2, changePercent);
            ps.setString(3, symbol);
            ps.executeUpdate();
            db.close();

            System.out.println("✅ Updated " + symbol + " → " + price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
