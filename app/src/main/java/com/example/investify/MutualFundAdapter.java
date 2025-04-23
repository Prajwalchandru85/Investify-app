package com.example.investify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investify.model.MutualFund;

import java.util.List;

public class MutualFundAdapter extends RecyclerView.Adapter<MutualFundAdapter.ViewHolder> {

    private List<MutualFund> funds;

    public MutualFundAdapter(List<MutualFund> funds) {
        this.funds = funds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mutual_fund, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MutualFund fund = funds.get(position);
        holder.textViewName.setText(fund.getName());
        holder.textViewCategory.setText("Category: " + fund.getCategory());
        holder.textViewReturns.setText("Returns: " + fund.getReturns());
        holder.textViewRisk.setText("Risk: " + fund.getRisk());
    }

    @Override
    public int getItemCount() {
        return funds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewCategory, textViewReturns, textViewRisk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewFundName);
            textViewCategory = itemView.findViewById(R.id.textViewFundCategory);
            textViewReturns = itemView.findViewById(R.id.textViewFundReturns);
            textViewRisk = itemView.findViewById(R.id.textViewFundRisk);
        }
    }
}
