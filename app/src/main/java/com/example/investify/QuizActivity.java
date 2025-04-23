package com.example.investify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestionNumber, tvQuestion;
    private RadioGroup radioGroup;
    private RadioButton rbOptionA, rbOptionB, rbOptionC, rbOptionD;
    private Button btnNext;
    private CardView quizCard;

    private DatabaseHelper databaseHelper;
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("EMAIL");

        // If email is not provided, check SharedPreferences
        if (userEmail == null || userEmail.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            userEmail = sharedPreferences.getString("EMAIL", "");

            // If still no email, redirect to login
            if (userEmail.isEmpty()) {
                Toast.makeText(this, "Please login to take the quiz", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(QuizActivity.this, LoginActivity.class);
                loginIntent.putExtra("REDIRECT_TO", "QUIZ");
                startActivity(loginIntent);
                finish();
                return;
            }
        }

        // Initialize UI elements
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestion = findViewById(R.id.tvQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        rbOptionA = findViewById(R.id.rbOptionA);
        rbOptionB = findViewById(R.id.rbOptionB);
        rbOptionC = findViewById(R.id.rbOptionC);
        rbOptionD = findViewById(R.id.rbOptionD);
        btnNext = findViewById(R.id.btnNext);
        quizCard = findViewById(R.id.quizCard);

        // Set up database and load questions
        databaseHelper = new DatabaseHelper(this);
        loadQuestions();

        // If questions were loaded successfully, display the first question
        if (!quizQuestions.isEmpty()) {
            displayQuestion(currentQuestionIndex);
        } else {
            Toast.makeText(this, "Failed to load quiz questions", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set click listener for Next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if an option is selected
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the selected answer is correct
                RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                String selectedAnswer = selectedRadioButton.getText().toString();
                QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

                if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                    correctAnswers++;
                }

                // Move to the next question or finish the quiz
                currentQuestionIndex++;

                if (currentQuestionIndex < quizQuestions.size()) {
                    displayQuestion(currentQuestionIndex);
                } else {
                    // Quiz is finished, save result and go to result activity
                    databaseHelper.saveQuizResult(userEmail, correctAnswers);

                    Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                    intent.putExtra("SCORE", correctAnswers);
                    intent.putExtra("TOTAL", quizQuestions.size());
                    intent.putExtra("EMAIL", userEmail);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void loadQuestions() {
        quizQuestions = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllQuizQuestions();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int questionId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTION_ID));
                @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTION));
                @SuppressLint("Range") String optionA = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION_A));
                @SuppressLint("Range") String optionB = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION_B));
                @SuppressLint("Range") String optionC = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION_C));
                @SuppressLint("Range") String optionD = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION_D));
                @SuppressLint("Range") String correctAnswer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CORRECT_ANSWER));

                QuizQuestion quizQuestion = new QuizQuestion(questionId, question, optionA, optionB, optionC, optionD, correctAnswer);
                quizQuestions.add(quizQuestion);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void displayQuestion(int index) {
        QuizQuestion question = quizQuestions.get(index);

        // Update UI with the current question
        tvQuestionNumber.setText("Question " + (index + 1) + " of " + quizQuestions.size());
        tvQuestion.setText(question.getQuestion());
        rbOptionA.setText(question.getOptionA());
        rbOptionB.setText(question.getOptionB());
        rbOptionC.setText(question.getOptionC());
        rbOptionD.setText(question.getOptionD());

        // Clear the selection
        radioGroup.clearCheck();

        // Update the Next button text for the last question
        if (index == quizQuestions.size() - 1) {
            btnNext.setText("Finish");
        }
    }

    // QuizQuestion class to hold question data
    private static class QuizQuestion {
        private int id;
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;

        public QuizQuestion(int id, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
            this.id = id;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
        }

        public int getId() {
            return id;
        }

        public String getQuestion() {
            return question;
        }

        public String getOptionA() {
            return optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
