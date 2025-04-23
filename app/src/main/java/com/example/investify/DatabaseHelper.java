package com.example.investify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "investify.db";
    private static final int DATABASE_VERSION = 3;

    // User table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PASSWORD = "password";

    public static final String COLUMN_INCOME = "income";

    public static final String COLUMN_BUDGET = "budget";


    // Expense table
    public static final String TABLE_EXPENSES = "expenses";
    public static final String EXPENSE_ID = "expense_id";
    public static final String EXPENSE_DATE = "date";
    public static final String EXPENSE_CATEGORY = "category";
    public static final String EXPENSE_AMOUNT = "amount";

    // Quiz questions table
    public static final String TABLE_QUIZ_QUESTIONS = "quiz_questions";
    public static final String COLUMN_QUESTION_ID = "question_id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION_A = "option_a";
    public static final String COLUMN_OPTION_B = "option_b";
    public static final String COLUMN_OPTION_C = "option_c";
    public static final String COLUMN_OPTION_D = "option_d";
    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";

    // Quiz results table
    public static final String TABLE_QUIZ_RESULTS = "quiz_results";
    public static final String COLUMN_RESULT_ID = "result_id";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_TIMESTAMP = "timestamp";


    // Create table statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, "
            + COLUMN_PHONE + " TEXT NOT NULL, "
            + COLUMN_AGE + " INTEGER NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_INCOME + " INTEGER NOT NULL, "
            + COLUMN_BUDGET + " INTEGER NOT NULL"
            + ")";


    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSES + "("
            + EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EXPENSE_DATE + " TEXT NOT NULL, "
            + EXPENSE_CATEGORY + " TEXT NOT NULL, "
            + EXPENSE_AMOUNT + " REAL NOT NULL"
            + ")";

    private static final String CREATE_TABLE_QUIZ_QUESTIONS = "CREATE TABLE " + TABLE_QUIZ_QUESTIONS + "("
            + COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_QUESTION + " TEXT NOT NULL, "
            + COLUMN_OPTION_A + " TEXT NOT NULL, "
            + COLUMN_OPTION_B + " TEXT NOT NULL, "
            + COLUMN_OPTION_C + " TEXT NOT NULL, "
            + COLUMN_OPTION_D + " TEXT NOT NULL, "
            + COLUMN_CORRECT_ANSWER + " TEXT NOT NULL"
            + ")";

    private static final String CREATE_TABLE_QUIZ_RESULTS = "CREATE TABLE " + TABLE_QUIZ_RESULTS + "("
            + COLUMN_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL, "
            + COLUMN_SCORE + " INTEGER NOT NULL, "
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(CREATE_TABLE_QUIZ_QUESTIONS);
        db.execSQL(CREATE_TABLE_QUIZ_RESULTS);
        prepopulateQuizQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL(CREATE_TABLE_QUIZ_QUESTIONS);
            db.execSQL(CREATE_TABLE_QUIZ_RESULTS);
            prepopulateQuizQuestions(db);
        }
    }

    private void prepopulateQuizQuestions(SQLiteDatabase db) {
        // Financial questions about Indian markets
        addQuizQuestion(db, "What is the full form of SEBI?",
                "Securities and Exchange Board of India",
                "Share Exchange Board of India",
                "Stock Exchange Bureau of India",
                "Securities and Equity Board of India",
                "Securities and Exchange Board of India");

        addQuizQuestion(db, "Which of the following is NOT a type of mutual fund?",
                "Equity Fund",
                "Debt Fund",
                "Penny Fund",
                "Hybrid Fund",
                "Penny Fund");

        addQuizQuestion(db, "What is the minimum investment amount for a Systematic Investment Plan (SIP) in most Indian mutual funds?",
                "₹100",
                "₹500",
                "₹1,000",
                "₹5,000",
                "₹500");

        addQuizQuestion(db, "What is the term for the process of converting physical share certificates to electronic form?",
                "Dematerialization",
                "Rematerialization",
                "Digitization",
                "Electronic Transfer",
                "Dematerialization");

        addQuizQuestion(db, "Which Indian index tracks the performance of the 30 largest and most actively traded stocks on the Bombay Stock Exchange?",
                "Nifty 50",
                "Sensex",
                "BSE 100",
                "Nifty Bank",
                "Sensex");

        // Add more questions here
        addQuizQuestion(db, "What is the full form of NAV in the context of mutual funds?",
                "New Asset Value",
                "Net Asset Value",
                "National Asset Verification",
                "Net Annual Value",
                "Net Asset Value");

        addQuizQuestion(db, "Which regulatory body governs the insurance sector in India?",
                "SEBI",
                "RBI",
                "IRDAI",
                "PFRDA",
                "IRDAI");

        addQuizQuestion(db, "What is the minimum lock-in period for tax-saving ELSS mutual funds in India?",
                "1 year",
                "3 years",
                "5 years",
                "7 years",
                "3 years");

        addQuizQuestion(db, "What is the term for the difference between the buying price and selling price of a security?",
                "Margin",
                "Spread",
                "Commission",
                "Premium",
                "Spread");

        addQuizQuestion(db, "Which of the following is NOT a type of bond available in India?",
                "Government Securities",
                "Corporate Bonds",
                "Municipal Bonds",
                "Future Bonds",
                "Future Bonds");
        // Add these to the prepopulateQuizQuestions method in DatabaseHelper.java
        addQuizQuestion(db, "What is the name of the tax-saving investment option in India with a lock-in period of 5 years?",
                "Public Provident Fund (PPF)",
                "National Savings Certificate (NSC)",
                "Tax-Saving Fixed Deposit",
                "National Pension System (NPS)",
                "Public Provident Fund (PPF)");

        addQuizQuestion(db, "Which of the following is NOT a type of market index in India?",
                "Nifty 50",
                "BSE Sensex",
                "BSE SmallCap",
                "India 100",
                "India 100");

        addQuizQuestion(db, "Which regulatory body regulates and supervises the pension funds in India?",
                "SEBI",
                "RBI",
                "PFRDA",
                "IRDAI",
                "PFRDA");

        addQuizQuestion(db, "What is the maximum tax deduction allowed under Section 80C of the Income Tax Act in India?",
                "₹1,00,000",
                "₹1,50,000",
                "₹2,00,000",
                "₹2,50,000",
                "₹1,50,000");

        addQuizQuestion(db, "Which of the following is NOT a benefit of investing in Mutual Funds?",
                "Professional Management",
                "Diversification",
                "Liquidity",
                "Guaranteed Returns",
                "Guaranteed Returns");

        addQuizQuestion(db, "What does P/E ratio stand for in stock market terminology?",
                "Price to Earnings Ratio",
                "Profit to Expense Ratio",
                "Purchase to Equity Ratio",
                "Placement to Exchange Ratio",
                "Price to Earnings Ratio");

        addQuizQuestion(db, "Which of the following is considered the safest investment option in India?",
                "Equity Shares",
                "Corporate Bonds",
                "Government Securities",
                "Real Estate",
                "Government Securities");

        addQuizQuestion(db, "What is the term for a market where prices of securities are falling continuously?",
                "Bull Market",
                "Bear Market",
                "Stag Market",
                "Shark Market",
                "Bear Market");

        addQuizQuestion(db, "What is the minimum investment amount for most Sovereign Gold Bonds (SGBs) in India?",
                "1 gram",
                "5 grams",
                "10 grams",
                "100 grams",
                "1 gram");

        addQuizQuestion(db, "Which of the following is NOT a type of risk associated with investments?",
                "Market Risk",
                "Liquidity Risk",
                "Credit Risk",
                "Satisfaction Risk",
                "Satisfaction Risk");

        addQuizQuestion(db, "Which Indian institution is responsible for currency management and banking regulations?",
                "SEBI",
                "RBI",
                "IRDAI",
                "NABARD",
                "RBI");

        addQuizQuestion(db, "What does SIP stand for in mutual fund investments?",
                "Systematic Investment Plan",
                "Scheduled Investment Proposal",
                "Strategic Investment Protocol",
                "Sustained Investment Procedure",
                "Systematic Investment Plan");

        addQuizQuestion(db, "Which of the following statements about ULIP (Unit Linked Insurance Plan) is FALSE?",
                "It combines investment and insurance",
                "It has a mandatory lock-in period of 5 years",
                "It offers guaranteed returns",
                "It provides life cover",
                "It offers guaranteed returns");

        addQuizQuestion(db, "What is the current Securities Transaction Tax (STT) rate for equity delivery-based transactions in India?",
                "0.1%",
                "0.2%",
                "0.5%",
                "1.0%",
                "0.1%");

        addQuizQuestion(db, "Which of these is NOT typically considered a defensive stock sector?",
                "Healthcare",
                "Utilities",
                "Consumer Staples",
                "Technology",
                "Technology");
    }

    // Add more quiz questions during initialization
    private void addQuizQuestion(SQLiteDatabase db, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_OPTION_A, optionA);
        values.put(COLUMN_OPTION_B, optionB);
        values.put(COLUMN_OPTION_C, optionC);
        values.put(COLUMN_OPTION_D, optionD);
        values.put(COLUMN_CORRECT_ANSWER, correctAnswer);
        db.insert(TABLE_QUIZ_QUESTIONS, null, values);


    }

    // Add a new user
    public long addUser(String name, String email, String phone, int age, String password,int income,int budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_INCOME,income);
        values.put(COLUMN_BUDGET,budget);


        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    // Check if user exists with given email and password
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    // Check if email already exists
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    // Get user details by email
    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_AGE};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        return db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
    }

    // ********** EXPENSE TRACKER FUNCTIONS **********

    // Add an expense
    public long addExpense(String date, String category, float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXPENSE_DATE, date);
        values.put(EXPENSE_CATEGORY, category);
        values.put(EXPENSE_AMOUNT, amount);

        long id = db.insert(TABLE_EXPENSES, null, values);
        db.close();
        return id;
    }

    // Get all expenses
    public Cursor getExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EXPENSES, null);
    }

    // Update an existing expense
    public void updateExpense(int expenseId, String date, String category, float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXPENSE_DATE, date);
        values.put(EXPENSE_CATEGORY, category);
        values.put(EXPENSE_AMOUNT, amount);

        db.update(TABLE_EXPENSES, values, EXPENSE_ID + "=?", new String[]{String.valueOf(expenseId)});
        db.close();
    }

    // Delete an expense
    public void deleteExpense(int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, EXPENSE_ID + "=?", new String[]{String.valueOf(expenseId)});
        db.close();
    }

    public Cursor getExpenseById(int expenseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EXPENSES + " WHERE " + EXPENSE_ID + " = ?", new String[]{String.valueOf(expenseId)});
    }

    // ********** QUIZ FUNCTIONS **********

    // Get all quiz questions
    public Cursor getAllQuizQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUIZ_QUESTIONS + " ORDER BY RANDOM() LIMIT 20", null);
    }

    // Save quiz result
    public long saveQuizResult(String userEmail, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_SCORE, score);

        long id = db.insert(TABLE_QUIZ_RESULTS, null, values);
        db.close();
        return id;
    }

    // Get leaderboard (top 10 scores)
    public Cursor getLeaderboard() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT u." + COLUMN_NAME + ", r." + COLUMN_USER_EMAIL + ", r." + COLUMN_SCORE + ", r." + COLUMN_TIMESTAMP +
                        " FROM " + TABLE_QUIZ_RESULTS + " r" +
                        " LEFT JOIN " + TABLE_USERS + " u ON r." + COLUMN_USER_EMAIL + " = u." + COLUMN_EMAIL +
                        " ORDER BY r." + COLUMN_SCORE + " DESC LIMIT 10", null);
    }
    // Get income by email and password (used during login)
    public int getIncome(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int income = -1;

        String[] columns = {COLUMN_INCOME};
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                income = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME));
            }
            cursor.close();
        }

        db.close();
        return income;
    }
    public int getBudget(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        int budget = -1;

        String[] columns = {COLUMN_BUDGET};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                budget = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET));
            }
            cursor.close();
        }

        db.close();
        return budget;
    }


}