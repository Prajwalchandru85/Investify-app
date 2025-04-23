package com.example.investify.model;

import com.google.gson.annotations.SerializedName;

public class GlobalQuoteResponse {
    @SerializedName("Global Quote")
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

    public static class GlobalQuote {
        @SerializedName("01. symbol")
        private String symbol;

        @SerializedName("02. open")
        private String open;

        @SerializedName("03. high")
        private String high;

        @SerializedName("04. low")
        private String low;

        @SerializedName("05. price")
        private String price;

        @SerializedName("09. change")
        private String change;

        @SerializedName("10. change percent")
        private String changePercent;

        public String getSymbol() {
            return symbol;
        }

        public String getPrice() {
            return price;
        }

        public String getChange() {
            return change;
        }

        public String getChangePercent() {
            return changePercent;
        }
    }
}
