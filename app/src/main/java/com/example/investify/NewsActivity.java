package com.example.investify;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investify.api.AlphaVantageApi;
import com.example.investify.api.ApiClient;
import com.example.investify.model.NewsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNews;
    private ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    private AlphaVantageApi apiService;
    private final String API_KEY = "Enter ur api key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Initialize API service
        apiService = ApiClient.getClient().create(AlphaVantageApi.class);

        // Initialize UI elements
        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        progressBar = findViewById(R.id.progressBar);

        // Setup RecyclerView
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(new ArrayList<>());
        recyclerViewNews.setAdapter(newsAdapter);

        // Fetch news
        fetchNews();
    }

    private void fetchNews() {
        progressBar.setVisibility(View.VISIBLE);

        // Make API call to Alpha Vantage to get news
        Call<NewsResponse> call = apiService.getNews(
                "NEWS_SENTIMENT", "financial_markets,economy", API_KEY);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<NewsResponse.NewsItem> newsItems = response.body().getFeed();
                    if (newsItems != null && !newsItems.isEmpty()) {
                        newsAdapter.updateNews(newsItems);
                    } else {
                        Toast.makeText(NewsActivity.this,
                                "No news available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewsActivity.this,
                            "Failed to fetch news", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NewsActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
