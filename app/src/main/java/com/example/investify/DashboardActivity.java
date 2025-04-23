package com.example.investify;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewDashboardTitle;
    private ImageView imageViewLogo;
    private ImageView profileIcon;
    DrawerLayout drawerLayout;
    private MaterialCardView cardDashboard;
    private MaterialButton btnPlanInvestments, btnExpenseTracker, btnNews, btnFinanceQuiz,btnfabLearnNow;
    private String userEmail;
    private int userIncome;
    private Cursor name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("EMAIL");
        userIncome = getIntent().getIntExtra("INCOME", 0);
       NavigationView navigationView = findViewById(R.id.navigation_view);

        // Initialize UI elements
        imageViewLogo = findViewById(R.id.imageViewLogo);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewDashboardTitle = findViewById(R.id.textViewDashboardTitle);
        cardDashboard = findViewById(R.id.cardDashboard);
        btnPlanInvestments = findViewById(R.id.btnPlanInvestments);
        btnExpenseTracker = findViewById(R.id.btnExpenseTracker);
        btnNews = findViewById(R.id.btnNews);
        btnFinanceQuiz = findViewById(R.id.btnFinanceQuiz);
        btnfabLearnNow=findViewById(R.id.fabLearnNow);

        // Set welcome message
        if (userEmail != null) {
            textViewWelcome.setText("Welcome, " + userEmail);
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        profileIcon = findViewById(R.id.profile_icon);

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_expense) {
                // TODO: Handle "expense" click
                Intent intent = new Intent(DashboardActivity.this, ExpenseTrackerActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else if (id == R.id.nav_investments) {
                // TODO: Handle "Invests" click
                Intent intent = new Intent(DashboardActivity.this, InvestmentPlanActivity.class);
                intent.putExtra("INCOME", userIncome);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);

// Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else if (id == R.id.nav_chatbot) {
                // TODO: Handle "chatbot" click
                Intent intent = new Intent(DashboardActivity.this, ChatbotActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (id == R.id.nav_quiz) {
                // TODO: Handle "quiz" click
                Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (id == R.id.nav_news) {
                // TODO: Handle "quiz" click
                Intent intent = new Intent(DashboardActivity.this, NewsActivity.class);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else if (id == R.id.nav_close) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        });


        // Apply entrance animations
        applyEntranceAnimations();

        // Add button click animations
        setupButtonClickAnimations();

        // Set click listeners
        setupClickListeners();
    }

    private void applyEntranceAnimations() {
        // Set initial states for animations
        imageViewLogo.setAlpha(0f);
        textViewWelcome.setAlpha(0f);
        textViewWelcome.setTranslationY(50f);
        textViewDashboardTitle.setAlpha(0f);
        textViewDashboardTitle.setTranslationY(50f);
        cardDashboard.setAlpha(0f);
        cardDashboard.setTranslationY(100f);
        ExtendedFloatingActionButton fabLearnNow = findViewById(R.id.fabLearnNow);
        fabLearnNow.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(800)  // Adjust timing as needed
                .start();


        // Logo animation
        imageViewLogo.animate()
                .alpha(1f)
                .setDuration(1000)
                .setStartDelay(300)
                .start();

        // Welcome text animation
        textViewWelcome.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(1000)
                .setStartDelay(600)
                .setInterpolator(new OvershootInterpolator())
                .start();

        // Dashboard title animation
        textViewDashboardTitle.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(1000)
                .setStartDelay(800)
                .setInterpolator(new OvershootInterpolator())
                .start();

        // Card animation
        cardDashboard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(1000)
                .setStartDelay(1000)
                .setInterpolator(new OvershootInterpolator(0.8f))
                .start();

        // Staggered animation for buttons (will be done in XML)
        animateButtonsSequentially();
    }

    private void animateButtonsSequentially() {
        // Hide buttons initially - we'll animate them after the card appears
        btnPlanInvestments.setAlpha(0f);
        btnExpenseTracker.setAlpha(0f);
        btnNews.setAlpha(0f);
        btnFinanceQuiz.setAlpha(0f);

        // Animate buttons one after another
        btnPlanInvestments.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(500)
                .setStartDelay(1200)
                .start();

        btnExpenseTracker.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(500)
                .setStartDelay(1400)
                .start();

        btnNews.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(500)
                .setStartDelay(1600)
                .start();

        btnFinanceQuiz.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(500)
                .setStartDelay(1800)
                .start();
    }

    private void setupButtonClickAnimations() {
        // Add animation to each button
        addClickAnimation(btnPlanInvestments);
        addClickAnimation(btnExpenseTracker);
        addClickAnimation(btnNews);
        addClickAnimation(btnFinanceQuiz);
    }

    private void addClickAnimation(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.performClick();
                    }
                    break;
            }
            // Return true to intercept the click event since we're calling performClick() manually
            return true;
        });
    }

    private void setupClickListeners() {
        btnPlanInvestments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent investIntent = new Intent(DashboardActivity.this, InvestmentPlanActivity.class);
                investIntent.putExtra("INCOME", userIncome);
                startActivity(investIntent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnExpenseTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ExpenseTrackerActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NewsActivity.class);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnFinanceQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnfabLearnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ChatbotActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);

                // Add activity transition animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            // Logout and go back to main activity
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            // Add activity transition animation for logout
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}