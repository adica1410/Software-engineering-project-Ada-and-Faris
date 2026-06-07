package com.example.focusmodeapp

data class SessionResponse(
    val id: Int,
    val user_id: Int,
    val start_time: String,
    val end_time: String?,
    val duration_minutes: Int,
    val duration_seconds: Int,
    val status: String,
    val created_at: String
)