package com.example.focusmodeapp

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val password: String
)