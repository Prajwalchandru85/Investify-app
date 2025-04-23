package com.example.investify;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ChatbotActivity extends AppCompatActivity {

    private TextView chatHistory;
    private EditText userInput;
    private final OkHttpClient client = new OkHttpClient();

    // Replace with your OpenRouter API key
    private final String API_KEY = "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";  // <-- Replace this!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        chatHistory = findViewById(R.id.chatHistory);
        userInput = findViewById(R.id.userInput);
        Button sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String question = userInput.getText().toString().trim();
            if (!question.isEmpty()) {
                updateChat("You: " + question);
                userInput.setText("");
                callOpenRouterAPI(question);
            }
        });
    }

    private void updateChat(String text) {
        runOnUiThread(() -> chatHistory.append(text + "\n\n"));
    }

    private void callOpenRouterAPI(String prompt) {
        try {
            JSONObject json = new JSONObject();
            json.put("model", "openai/gpt-3.5-turbo"); // or any available model
            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.put(userMessage);
            json.put("messages", messages);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url("https://openrouter.ai/api/v1/chat/completions")
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    updateChat("Error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        try {
                            JSONObject responseObject = new JSONObject(res);
                            JSONArray choices = responseObject.getJSONArray("choices");
                            JSONObject firstChoice = choices.getJSONObject(0);
                            JSONObject message = firstChoice.getJSONObject("message");
                            String content = message.getString("content");
                            updateChat("InvestifyBot: " + content.trim());
                        } catch (Exception e) {
                            updateChat("Parsing error: " + e.getMessage());
                        }
                    } else {
                        updateChat("API error: " + response.message());
                    }
                }
            });

        } catch (Exception e) {
            updateChat("Exception: " + e.getMessage());
        }
    }
}
