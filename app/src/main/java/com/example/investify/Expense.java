package com.example.investify;


public class Expense {
    private String category;
    private float amount;

    public Expense(String category, float amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public float getAmount() {
        return amount;
    }
}

