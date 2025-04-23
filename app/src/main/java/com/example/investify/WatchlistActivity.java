package com.example.investify;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class WatchlistActivity extends AppCompatActivity {

    private ListView listViewWatchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        listViewWatchlist = findViewById(R.id.listViewWatchlist);

        // Get the watchlist from SharedPreferences
        List<String> watchlist = WatchlistUtils.getWatchlist(this);

        // If watchlist is not empty, set up ListView
        if (watchlist != null && !watchlist.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, watchlist);
            listViewWatchlist.setAdapter(adapter);
        }

        listViewWatchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click (you could show details of the stock, for example)
            }
        });
    }
}
