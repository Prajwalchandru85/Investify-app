package com.example.investify;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnLogin, btnSignup;
    private ImageView imageViewLogo;
    private TextView textViewWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        imageViewLogo = findViewById(R.id.imageViewLogo);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // Apply entrance animations
        applyEntranceAnimations();

        // Add button click animations
        setupButtonClickAnimations();

        // Set click listeners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void applyEntranceAnimations() {
        // Set initial states (already set in XML)

        // Logo animation - bounce in from top
        imageViewLogo.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(1200)
                .setStartDelay(300)
                .setInterpolator(new OvershootInterpolator(0.7f))
                .start();

        // Welcome text animation - fade in
        textViewWelcome.animate()
                .alpha(1f)
                .setDuration(800)
                .setStartDelay(700)
                .start();

        // Login button animation
        btnLogin.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(800)
                .setStartDelay(1000)
                .setInterpolator(new OvershootInterpolator(0.5f))
                .start();

        // Signup button animation
        btnSignup.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(800)
                .setStartDelay(1200)
                .setInterpolator(new OvershootInterpolator(0.5f))
                .start();
    }

    private void setupButtonClickAnimations() {
        // Add animation to each button
        addClickAnimation(btnLogin);
        addClickAnimation(btnSignup);
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

    @Override
    public void onBackPressed() {
        // Adding exit animation if needed
        super.onBackPressed();
    }
}