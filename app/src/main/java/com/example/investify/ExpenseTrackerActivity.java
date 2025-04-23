package com.example.investify;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.*;

public class ExpenseTrackerActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private Spinner spinnerCategory;
    private Button buttonSelectDate, buttonAddExpense;
    private TextView textViewSelectedDate;
    private ListView listViewExpenses;
    private PieChart pieChart;
    private TextView totalExpenseTextView;
    private TextView highestCategoryTextView;


    private ArrayAdapter<String> expenseAdapter;
    private ArrayList<String> expenseList;
    private HashMap<String, Float> categoryMap;
    private HashMap<Integer, String> expenseIdMap;  // Stores expense IDs

    private DatabaseHelper dbHelper;
    private String selectedDate = "";
    private int userBudget = 0;
    private TextView budgetTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_tracker);
        String email = getIntent().getStringExtra("EMAIL");
        String password = getIntent().getStringExtra("PASSWORD");

        dbHelper = new DatabaseHelper(this);
        userBudget = dbHelper.getBudget(email);




        editTextAmount = findViewById(R.id.editTextAmount);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);
        buttonAddExpense = findViewById(R.id.buttonAddExpense);
        listViewExpenses = findViewById(R.id.listViewExpenses);
        pieChart = findViewById(R.id.pieChart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHelper = new DatabaseHelper(this);
        expenseList = new ArrayList<>();
        expenseIdMap = new HashMap<>();
        expenseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseList);
        listViewExpenses.setAdapter(expenseAdapter);
        totalExpenseTextView = findViewById(R.id.totalExpenseTextView);
        highestCategoryTextView = findViewById(R.id.highestCategoryTextView);
        budgetTextView = findViewById(R.id.budgetTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }


        createNotificationChannel();


        // Load budget from SharedPreferences
        //SharedPreferences prefs = getSharedPreferences("BudgetPrefs", MODE_PRIVATE);
        //userBudget = prefs.getFloat("user_budget", 0);



        categoryMap = new HashMap<>();
        String[] categories = {"Petrol", "Shopping", "Grocery", "Stationary", "Milk", "House Products", "Party"};
        spinnerCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        buttonSelectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                selectedDate = day + "/" + (month + 1) + "/" + year;
                textViewSelectedDate.setText("Date: " + selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        buttonAddExpense.setOnClickListener(v -> {
            String amountText = editTextAmount.getText().toString();
            if (amountText.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Enter all details!", Toast.LENGTH_SHORT).show();
                return;
            }
            dbHelper.addExpense(selectedDate, spinnerCategory.getSelectedItem().toString(), Float.parseFloat(amountText));
            loadExpenses();
        });

        // Handle Edit/Delete on clicking an expense item
        listViewExpenses.setOnItemClickListener((parent, view, position, id) -> {
            int expenseId = Integer.parseInt(expenseIdMap.get(position)); // Get Expense ID
            showEditDeleteDialog(expenseId);
        });

        loadExpenses();
    }

    // Load expenses from database
    private void loadExpenses() {
        expenseList.clear();
        categoryMap.clear();
        expenseIdMap.clear();
        Cursor cursor = dbHelper.getExpenses();
        int index = 0;
        while (cursor.moveToNext()) {
            int expenseId = cursor.getInt(0);
            String entry = cursor.getString(1) + " - " + cursor.getString(2) + ": ₹" + cursor.getFloat(3);
            expenseList.add(entry);
            categoryMap.put(cursor.getString(2), categoryMap.getOrDefault(cursor.getString(2), 0f) + cursor.getFloat(3));
            expenseIdMap.put(index++, String.valueOf(expenseId)); // Store expense ID for edit/delete
        }
        cursor.close();
        expenseAdapter.notifyDataSetChanged();
        updatePieChart();
        updateSummaryWidgets();

    }
    private void updateSummaryWidgets() {
        float totalExpense = 0;
        String highestCategory = "";
        float highestAmount = 0;

        for (Map.Entry<String, Float> entry : categoryMap.entrySet()) {
            float amount = entry.getValue();
            totalExpense += amount;
            if (amount > highestAmount) {
                highestAmount = amount;
                highestCategory = entry.getKey();
            }
        }

        totalExpenseTextView.setText("Total Expense: ₹ " + String.format("%.2f", totalExpense));

        if (!highestCategory.isEmpty()) {
            highestCategoryTextView.setText("💸 Highest Spending: " + highestCategory.toUpperCase() + " (₹" + highestAmount + ")");
            highestCategoryTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            highestCategoryTextView.setTextSize(18);
            highestCategoryTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        float remaining = userBudget - totalExpense;
        budgetTextView.setText("₹ " + String.format("%.2f", remaining) + " left");

        if (totalExpense > userBudget) {
            sendBudgetExceededNotification(totalExpense);
        }

    }




    // Update Pie Chart
    private void updatePieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : categoryMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Expenses");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(new PieData(dataSet));
        pieChart.invalidate();
    }


    // Show dialog to edit or delete an expense
    private void showEditDeleteDialog(int expenseId) {
        new AlertDialog.Builder(this)
                .setTitle("Edit or Delete")
                .setMessage("What do you want to do?")
                .setPositiveButton("Edit", (dialog, which) -> showEditExpenseDialog(expenseId))
                .setNegativeButton("Delete", (dialog, which) -> {
                    dbHelper.deleteExpense(expenseId);  // Delete expense
                    loadExpenses();  // Refresh list
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

    // Show dialog to edit an expense
    private void showEditExpenseDialog(int expenseId) {
        Cursor cursor = dbHelper.getExpenseById(expenseId);
        if (!cursor.moveToFirst()) return;

        String oldDate = cursor.getString(1);
        String oldCategory = cursor.getString(2);
        float oldAmount = cursor.getFloat(3);
        cursor.close();

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_expense, null);
        EditText editAmount = dialogView.findViewById(R.id.editAmount);
        Spinner editCategory = dialogView.findViewById(R.id.editCategory);
        Button editDateButton = dialogView.findViewById(R.id.editDateButton);
        TextView editDateText = dialogView.findViewById(R.id.editDateText);

        editAmount.setText(String.valueOf(oldAmount));
        editDateText.setText(oldDate);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Petrol", "Shopping", "Grocery", "Stationary", "Milk", "House Products", "Party"});
        editCategory.setAdapter(adapter);
        editCategory.setSelection(adapter.getPosition(oldCategory));

        editDateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                String newDate = day + "/" + (month + 1) + "/" + year;
                editDateText.setText(newDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        new AlertDialog.Builder(this)
                .setTitle("Edit Expense")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newDate = editDateText.getText().toString();
                    String newCategory = editCategory.getSelectedItem().toString();
                    float newAmount = Float.parseFloat(editAmount.getText().toString());

                    dbHelper.updateExpense(expenseId, newDate, newCategory, newAmount);
                    loadExpenses(); // Refresh list
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "BudgetAlertChannel";
            String description = "Channel for budget alerts";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("budget_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    private void sendBudgetExceededNotification(float totalExpense) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "budget_channel")
                .setSmallIcon(R.drawable.ic_warning)  // Make sure this icon exists
                .setContentTitle("Budget Alert 🚨")
                .setContentText("You've exceeded your budget! Total: ₹" + String.format("%.2f", totalExpense))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1001, builder.build());
    }



}
