package com.example.investify;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investify.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private List<Stock> stocks;

    public StockAdapter(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.textViewSymbol.setText(stock.getSymbol());
        holder.textViewName.setText(stock.getName());
        holder.textViewPrice.setText("Price: " + stock.getPrice());
        holder.textViewChange.setText("Change: " + stock.getChange());

        // Set click listener for each stock item
        holder.itemView.setOnClickListener(v -> {
            Log.d("StockAdapter", "Item clicked: " + stock.getName());  // Log to check if click is triggered
            showAddToWatchlistDialog(stock, v);
        });
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSymbol, textViewName, textViewPrice, textViewChange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSymbol = itemView.findViewById(R.id.textViewStockSymbol);
            textViewName = itemView.findViewById(R.id.textViewStockName);
            textViewPrice = itemView.findViewById(R.id.textViewStockPrice);
            textViewChange = itemView.findViewById(R.id.textViewStockChange);
        }
    }

    // Method to show the Add to Watchlist dialog
    private void showAddToWatchlistDialog(Stock stock, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Do you want to add " + stock.getName() + " to your watchlist?")
                .setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Add stock to watchlist (store symbol)
                        List<String> watchlist = WatchlistUtils.getWatchlist(view.getContext());
                        if (watchlist == null) {
                            watchlist = new ArrayList<>();
                        }
                        // Add the stock symbol to the list
                        if (!watchlist.contains(stock.getSymbol())) {
                            watchlist.add(stock.getSymbol());
                            // Save updated watchlist
                            WatchlistUtils.saveWatchlist(view.getContext(), watchlist);
                            Toast.makeText(view.getContext(), stock.getName() + " added to watchlist", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Already in watchlist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);  // Dismiss dialog on "Cancel"

        // Create and show the alert dialog
        AlertDialog alert = builder.create();
        alert.show();
    }

}
