package com.example.focusmodeapp

data class ReminderResponse(
    val id: Int,
    val user_id: Int,
    val title: String,
    val reminder_time: String,
    val is_enabled: Int,
    val created_at: String
)