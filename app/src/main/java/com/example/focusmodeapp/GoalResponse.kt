package com.example.focusmodeapp

data class GoalResponse(
    val id: Int,
    val user_id: Int,
    val title: String,
    val goal_type: String,
    val target_minutes: Int,
    val current_minutes: Int
)