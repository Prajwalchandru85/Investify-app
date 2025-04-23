package com.example.investify;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WatchlistUtils {

    private static final String PREF_NAME = "watchlist_prefs";
    private static final String KEY_WATCHLIST = "watchlist";

    // Save the watchlist to SharedPreferences
    public static void saveWatchlist(Context context, List<String> watchlist) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(watchlist);
        editor.putString(KEY_WATCHLIST, json);
        editor.apply();
    }

    // Get the watchlist from SharedPreferences
    public static List<String> getWatchlist(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_WATCHLIST, null);
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}

