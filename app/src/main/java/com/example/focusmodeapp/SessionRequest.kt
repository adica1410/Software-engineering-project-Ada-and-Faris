package com.example.focusmodeapp

data class SessionRequest(
    val user_id: Int,
    val start_time: String,
    val end_time: String?,
    val duration_minutes: Int,
    val duration_seconds: Int,
    val status: String
)