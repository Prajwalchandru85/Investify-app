package com.example.investify.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TopGainersLosersResponse {
    @SerializedName("metadata")
    private String metadata;

    @SerializedName("last_updated")
    private String lastUpdated;

    @SerializedName("top_gainers")
    private List<StockItem> topGainers;

    @SerializedName("top_losers")
    private List<StockItem> topLosers;

    @SerializedName("most_actively_traded")
    private List<StockItem> mostActivelyTraded;

    public List<StockItem> getTopGainers() {
        return topGainers;
    }

    public List<StockItem> getMostActivelyTraded() {
        return mostActivelyTraded;
    }

    public static class StockItem {
        @SerializedName("ticker")
        private String ticker;

        @SerializedName("price")
        private String price;

        @SerializedName("change_amount")
        private String changeAmount;

        @SerializedName("change_percentage")
        private String changePercentage;

        @SerializedName("volume")
        private String volume;

        public String getTicker() {
            return ticker;
        }

        public String getPrice() {
            return price;
        }

        public String getChangeAmount() {
            return changeAmount;
        }

        public String getChangePercentage() {
            return changePercentage;
        }
    }
}
