package com.example.investify.api;

import com.example.investify.model.GlobalQuoteResponse;
import com.example.investify.model.NewsResponse;
import com.example.investify.model.TimeSeriesResponse;
import com.example.investify.model.TopGainersLosersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaVantageApi {
    @GET("query")
    Call<GlobalQuoteResponse> getStockQuote(
            @Query("function") String function,
            @Query("symbol") String symbol,
            @Query("apikey") String apiKey
    );

    @GET("query")
    Call<TopGainersLosersResponse> getTopGainersLosers(
            @Query("function") String function,
            @Query("apikey") String apiKey
    );

    @GET("query")
    Call<NewsResponse> getNews(
            @Query("function") String function,
            @Query("topics") String topics,
            @Query("apikey") String apiKey
    );

    @GET("query")
    Call<TimeSeriesResponse> getTimeSeriesMonthly(
            @Query("function") String function,
            @Query("symbol") String symbol,
            @Query("apikey") String apiKey
    );
}
