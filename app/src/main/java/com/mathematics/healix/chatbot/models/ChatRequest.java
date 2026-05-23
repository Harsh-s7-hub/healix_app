package com.mathematics.healix.chatbot.models;

public class ChatRequest {
     private String question;

    public ChatRequest(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
