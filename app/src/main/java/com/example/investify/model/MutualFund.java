package com.example.investify.model;

public class MutualFund {
    private String name;
    private String category;
    private String returns;
    private String risk;

    public MutualFund(String name, String category, String returns, String risk) {
        this.name = name;
        this.category = category;
        this.returns = returns;
        this.risk = risk;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getReturns() {
        return returns;
    }

    public String getRisk() {
        return risk;
    }
}
