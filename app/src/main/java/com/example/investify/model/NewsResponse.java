package com.example.investify.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {
    @SerializedName("feed")
    private List<NewsItem> feed;

    public List<NewsItem> getFeed() {
        return feed;
    }

    public static class NewsItem {
        @SerializedName("title")
        private String title;

        @SerializedName("summary")
        private String summary;

        @SerializedName("banner_image")
        private String bannerImage;

        @SerializedName("url")
        private String url;

        @SerializedName("time_published")
        private String timePublished;

        @SerializedName("source")
        private String source;

        public String getTitle() {
            return title;
        }

        public String getSummary() {
            return summary;
        }

        public String getSource() {
            return source;
        }

        public String getTimePublished() {
            return timePublished;
        }
    }
}
