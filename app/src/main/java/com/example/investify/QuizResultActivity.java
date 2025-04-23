package com.example.investify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvScore, tvMessage;
    private Button btnTryAgain, btnDashboard;
    private RecyclerView recyclerViewLeaderboard;
    private DatabaseHelper databaseHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get data from intent
        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 20);
        userEmail = getIntent().getStringExtra("EMAIL");

        // Initialize UI elements
        tvScore = findViewById(R.id.tvScore);
        tvMessage = findViewById(R.id.tvMessage);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnDashboard = findViewById(R.id.btnDashboard);
        recyclerViewLeaderboard = findViewById(R.id.recyclerViewLeaderboard);

        // Display score
        tvScore.setText(score + " / " + total);

        // Set message based on score
        if (score >= total * 0.8) {
            tvMessage.setText("Excellent! You have a strong understanding of financial concepts.");
        } else if (score >= total * 0.6) {
            tvMessage.setText("Good job! You have a decent grasp of financial concepts.");
        } else if (score >= total * 0.4) {
            tvMessage.setText("Not bad! There's room for improvement in your financial knowledge.");
        } else {
            tvMessage.setText("Keep learning! Consider reviewing basic financial concepts.");
        }

        // Set up leaderboard
        setupLeaderboard();

        // Set click listeners for buttons
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);
                finish();
            }
        });

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this, DashboardActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupLeaderboard() {
        List<LeaderboardItem> leaderboardItems = new ArrayList<>();
        Cursor cursor = databaseHelper.getLeaderboard();

        if (cursor.moveToFirst()) {
            int rank = 1;
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL));
                @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCORE));
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIMESTAMP));

                // If name is null, use the email
                if (name == null || name.isEmpty()) {
                    name = email.split("@")[0];
                }

                LeaderboardItem item = new LeaderboardItem(rank++, name, score, timestamp);
                leaderboardItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Set up RecyclerView with adapter
        LeaderboardAdapter adapter = new LeaderboardAdapter(leaderboardItems);
        recyclerViewLeaderboard.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLeaderboard.setAdapter(adapter);
    }

    // LeaderboardItem class to hold leaderboard data
    private static class LeaderboardItem {
        private int rank;
        private String name;
        private int score;
        private String timestamp;

        public LeaderboardItem(int rank, String name, int score, String timestamp) {
            this.rank = rank;
            this.name = name;
            this.score = score;
            this.timestamp = timestamp;
        }

        public int getRank() {
            return rank;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }

    // LeaderboardAdapter for displaying leaderboard items
    private class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
        private List<LeaderboardItem> leaderboardItems;

        public LeaderboardAdapter(List<LeaderboardItem> leaderboardItems) {
            this.leaderboardItems = leaderboardItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_leaderboard, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            LeaderboardItem item = leaderboardItems.get(position);
            holder.tvRank.setText(String.valueOf(item.getRank()));
            holder.tvName.setText(item.getName());
            holder.tvScore.setText(String.valueOf(item.getScore()));

            // Highlight the user's score
            if (item.getName().equals(userEmail) || item.getName().equals(userEmail.split("@")[0])) {
                holder.layout.setBackgroundResource(R.drawable.highlight_background);
            }
        }

        @Override
        public int getItemCount() {
            return leaderboardItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvRank, tvName, tvScore;
            LinearLayout layout;

            ViewHolder(View itemView) {
                super(itemView);
                tvRank = itemView.findViewById(R.id.tvRank);
                tvName = itemView.findViewById(R.id.tvName);
                tvScore = itemView.findViewById(R.id.tvLeaderboardScore);
                layout = itemView.findViewById(R.id.leaderboardItemLayout);
            }
        }
    }
}