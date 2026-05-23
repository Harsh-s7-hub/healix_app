package com.mathematics.healix.chatbot.ui;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mathematics.healix.R;
import com.mathematics.healix.chatbot.api.ApiService;
import com.mathematics.healix.chatbot.api.RetrofitClient;
import com.mathematics.healix.chatbot.models.ChatRequest;
import com.mathematics.healix.chatbot.models.ChatResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatbotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        testBackend();
    }

    private void testBackend() {

        ApiService apiService =
                RetrofitClient
                        .getClient()
                        .create(ApiService.class);

        ChatRequest request =
                new ChatRequest(
                        "What is Parkinson's disease?"
                );

        apiService.askQuestion(request)
                .enqueue(new Callback<ChatResponse>() {

                    @Override
                    public void onResponse(
                            Call<ChatResponse> call,
                            Response<ChatResponse> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            Log.d(
                                    "CHATBOT",
                                    response.body().getResponse()
                            );

                        } else {

                            Log.d(
                                    "CHATBOT",
                                    "Response failed"
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ChatResponse> call,
                            Throwable t
                    ) {

                        Log.e(
                                "CHATBOT",
                                Objects.requireNonNull(t.getMessage())
                        );
                    }
                });
    }
}