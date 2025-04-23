package com.example.investify;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class InvestmentCalculator {
    /**
     * Calculate future value using compound interest formula
     */
    public static double calculateFutureValue(double principal, double rate, int years, int compoundingPerYear) {
        Log.d("DEBUG", "Principal: " + principal);
        Log.d("DEBUG", "Rate: " + rate);
        Log.d("DEBUG", "Years: " + years);
        Log.d("DEBUG", "Compounding: " + compoundingPerYear);

        if (principal <= 0 || years <= 0) {
            Log.e("ERROR", "Invalid input values for FV calculation");
            return 0;
        }

        double r = rate / 100.0;
        int n = compoundingPerYear;
        int t = years;
        return principal * Math.pow(1 + (r/n), n*t);
    }

    /**
     * Get asset allocation based on risk profile and time horizon
     */
    public static Map<String, Double> getAssetAllocation(String riskProfile, int timeHorizon) {
        Map<String, Double> allocation = new HashMap<>();

        // Base allocations by risk profile
        if ("Conservative (Low Risk)".equals(riskProfile)) {
            allocation.put("Fixed Deposits", 50.0);
            allocation.put("Debt Mutual Funds", 30.0);
            allocation.put("Equity Mutual Funds", 15.0);
            allocation.put("Stocks", 5.0);
        } else if ("Moderate (Medium Risk)".equals(riskProfile)) {
            allocation.put("Fixed Deposits", 30.0);
            allocation.put("Debt Mutual Funds", 30.0);
            allocation.put("Equity Mutual Funds", 25.0);
            allocation.put("Stocks", 15.0);
        } else { // High Risk
            allocation.put("Fixed Deposits", 10.0);
            allocation.put("Debt Mutual Funds", 20.0);
            allocation.put("Equity Mutual Funds", 40.0);
            allocation.put("Stocks", 30.0);
        }

        // Adjust based on time horizon
        adjustForTimeHorizon(allocation, timeHorizon);

        return allocation;
    }

    /**
     * Adjust allocation based on investment time horizon
     */
    private static void adjustForTimeHorizon(Map<String, Double> allocation, int timeHorizon) {
        double equityAdjustment = 0.0;

        if (timeHorizon < 3) {
            // Short-term: reduce equity and stocks
            equityAdjustment = -10.0;
        } else if (timeHorizon >= 7) {
            // Long-term: increase equity and stocks
            equityAdjustment = 10.0;
        }

        // Apply adjustment (ensuring allocations stay in reasonable ranges)
        if (equityAdjustment != 0) {
            double equity = allocation.get("Equity Mutual Funds");
            double stocks = allocation.get("Stocks");
            double fd = allocation.get("Fixed Deposits");

            // Adjust equity and stocks
            double newEquity = Math.max(10.0, Math.min(60.0, equity + equityAdjustment/2));
            double newStocks = Math.max(5.0, Math.min(40.0, stocks + equityAdjustment/2));

            double totalAdjustment = (newEquity - equity) + (newStocks - stocks);

            allocation.put("Equity Mutual Funds", newEquity);
            allocation.put("Stocks", newStocks);

            // Take the adjustment from FDs
            double newFd = Math.max(5.0, fd - totalAdjustment);
            allocation.put("Fixed Deposits", newFd);

            // Ensure the total is still 100%
            double total = allocation.values().stream().mapToDouble(Double::doubleValue).sum();
            double debt = allocation.get("Debt Mutual Funds");
            allocation.put("Debt Mutual Funds", debt + (100 - total));
        }
    }

    /**
     * Calculate expected annual return based on asset allocation
     */
    public static double getExpectedReturn(Map<String, Double> allocation) {
        // Estimated returns for each asset class
        double fdReturn = 6.0; // 6% for FDs
        double debtReturn = 8.0; // 8% for debt funds
        double equityReturn = 12.0; // 12% for equity funds
        double stocksReturn = 15.0; // 15% for individual stocks

        double weightedReturn = 0.0;

        // Calculate weighted average return
        if (allocation.containsKey("Fixed Deposits")) {
            weightedReturn += (allocation.get("Fixed Deposits") / 100) * fdReturn;
        }

        if (allocation.containsKey("Debt Mutual Funds")) {
            weightedReturn += (allocation.get("Debt Mutual Funds") / 100) * debtReturn;
        }

        if (allocation.containsKey("Equity Mutual Funds")) {
            weightedReturn += (allocation.get("Equity Mutual Funds") / 100) * equityReturn;
        }

        if (allocation.containsKey("Stocks")) {
            weightedReturn += (allocation.get("Stocks") / 100) * stocksReturn;
        }

        return weightedReturn;
    }
}
