package com.example.focusmodeapp

data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)