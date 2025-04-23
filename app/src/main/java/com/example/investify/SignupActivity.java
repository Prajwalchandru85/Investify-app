package com.example.investify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone, editTextAge, editTextPassword, editTextConfirmPassword,editTextIncome,editTextBudget;
    private Button btnRegister;
    private TextView textViewLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAge = findViewById(R.id.editTextAge);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
        editTextIncome=findViewById(R.id.editTextIncome);
        editTextBudget=findViewById(R.id.editTextBudget);


        // Set click listeners
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        // Get input values
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String incomeStr=editTextIncome.getText().toString().trim();
        String budgetStr=editTextBudget.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter your name");
            editTextName.requestFocus();
            return;
        }
        int agee= Integer.parseInt(ageStr);
        if(agee<18) {
            editTextAge.setError("You are not eligible to register");
            editTextAge.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(phone)) {
            editTextPhone.setError("Please enter your phone number");
            editTextPhone.requestFocus();
            return;
        }
        if(phone.length()!=10)
        {
            editTextPhone.setError("Please enter a valid phone number");
            editTextPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ageStr)) {
            editTextAge.setError("Please enter your age");
            editTextAge.requestFocus();
            return;
        }



        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter a password");
            editTextPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(incomeStr)) {
            editTextName.setError("Please enter your income");
            editTextName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(budgetStr)) {
            editTextName.setError("Please enter your budget");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            editTextConfirmPassword.setError("Please confirm your password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords do not match");
            editTextConfirmPassword.requestFocus();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            editTextAge.setError("Please enter a valid age");
            editTextAge.requestFocus();
            return;
        }

        int income;
        try {
            income = Integer.parseInt(incomeStr);
            if (income <= 0) {
                editTextIncome.setError("Income must be greater than 0");
                editTextIncome.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            editTextIncome.setError("Please enter a valid income");
            editTextIncome.requestFocus();
            return;
        }

        int budget;
        try {
            budget = Integer.parseInt(budgetStr);
            if (budget <= 0) {
                editTextBudget.setError("Budget must be greater than 0");
                editTextBudget.requestFocus();
                return;
            }
            if (budget > income) {
                editTextBudget.setError("Budget cannot be greater than income");
                editTextBudget.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            editTextBudget.setError("Please enter a valid budget");
            editTextBudget.requestFocus();
            return;
        }



        // Check if email already exists
        if (databaseHelper.checkEmail(email)) {
            editTextEmail.setError("Email already exists");
            editTextEmail.requestFocus();
            return;
        }

        // Add user to database
        long result = databaseHelper.addUser(name, email, phone, age, password,income,budget);

        if (result > 0) {
            Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
