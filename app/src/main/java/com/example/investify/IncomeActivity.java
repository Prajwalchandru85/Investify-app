package com.example.investify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IncomeActivity extends AppCompatActivity {
    private EditText incomeEditText;
    private EditText editTextBudget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        incomeEditText = findViewById(R.id.incomeEditText);
        editTextBudget = findViewById(R.id.editTextBudget);
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    float income = Float.parseFloat(incomeEditText.getText().toString());
                    float budget = Float.parseFloat(editTextBudget.getText().toString());

                    // Save to SharedPreferences
                    SharedPreferences.Editor editor = getSharedPreferences("BudgetPrefs", MODE_PRIVATE).edit();
                    editor.putFloat("user_income", income);
                    editor.putFloat("user_budget", budget);
                    editor.apply();

                    Toast.makeText(IncomeActivity.this, "Income and Budget saved!", Toast.LENGTH_SHORT).show();

                    // Go to next screen
                    Intent intent = new Intent(IncomeActivity.this, InvestmentPlanActivity.class);
                    intent.putExtra("income", income);
                    startActivity(intent);
                }
            }
        });
    }


    private boolean validateInput() {
        String incomeStr = incomeEditText.getText().toString();
        String budgetStr = editTextBudget.getText().toString();

        if (incomeStr.isEmpty()) {
            Toast.makeText(this, "Please enter your income", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (budgetStr.isEmpty()) {
            Toast.makeText(this, "Please enter your budget", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            float income = Float.parseFloat(incomeStr);
            float budget = Float.parseFloat(budgetStr);
            if (income <= 0 || budget < 0) {
                Toast.makeText(this, "Values must be positive", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
