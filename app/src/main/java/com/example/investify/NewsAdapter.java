package com.example.investify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investify.model.NewsResponse;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsResponse.NewsItem> newsItems;

    public NewsAdapter(List<NewsResponse.NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsResponse.NewsItem newsItem = newsItems.get(position);
        holder.textViewTitle.setText(newsItem.getTitle());
        holder.textViewSummary.setText(newsItem.getSummary());
        holder.textViewSource.setText(newsItem.getSource());
        holder.textViewTime.setText(newsItem.getTimePublished());
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public void updateNews(List<NewsResponse.NewsItem> newItems) {
        this.newsItems = newItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewSummary, textViewSource, textViewTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewNewsTitle);
            textViewSummary = itemView.findViewById(R.id.textViewNewsSummary);
            textViewSource = itemView.findViewById(R.id.textViewNewsSource);
            textViewTime = itemView.findViewById(R.id.textViewNewsTime);
        }
    }

}
