package com.mathematics.healix.chatbot.api;


import com.mathematics.healix.chatbot.models.ChatRequest;
import com.mathematics.healix.chatbot.models.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("chat")
    Call<ChatResponse> askQuestion(
            @Body ChatRequest request
    );
}