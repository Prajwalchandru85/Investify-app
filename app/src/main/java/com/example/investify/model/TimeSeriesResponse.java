package com.example.investify.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class TimeSeriesResponse {
    @SerializedName("Meta Data")
    private MetaData metaData;

    @SerializedName("Monthly Time Series")
    private Map<String, TimeSeriesData> monthlyTimeSeries;

    public Map<String, TimeSeriesData> getMonthlyTimeSeries() {
        return monthlyTimeSeries;
    }

    public static class MetaData {
        @SerializedName("1. Information")
        private String information;

        @SerializedName("2. Symbol")
        private String symbol;

        @SerializedName("3. Last Refreshed")
        private String lastRefreshed;

        @SerializedName("4. Time Zone")
        private String timeZone;
    }

    public static class TimeSeriesData {
        @SerializedName("1. open")
        private String open;

        @SerializedName("2. high")
        private String high;

        @SerializedName("3. low")
        private String low;

        @SerializedName("4. close")
        private String close;

        @SerializedName("5. volume")
        private String volume;

        public String getClose() {
            return close;
        }
    }
}
