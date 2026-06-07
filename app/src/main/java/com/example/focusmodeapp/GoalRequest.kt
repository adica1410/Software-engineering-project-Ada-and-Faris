package com.example.focusmodeapp

data class GoalRequest(
    val user_id: Int,
    val title: String,
    val goal_type: String,
    val target_minutes: Int
)