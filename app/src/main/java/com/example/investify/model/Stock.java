package com.example.investify.model;

public class Stock {
    private String symbol;
    private String name;
    private String price;
    private String change;

    public Stock(String symbol, String name, String price, String change) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.change = change;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getChange() {
        return change;
    }
}
