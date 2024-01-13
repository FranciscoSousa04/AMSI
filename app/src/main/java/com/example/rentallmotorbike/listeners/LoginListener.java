package com.example.rentallmotorbike.listeners;

public interface LoginListener {
    void onLoginSuccess(String username);
    void onLoginError(String errorMessage);
}
