package com.example.focusmodeapp

data class StudyGoal(
    val name: String,
    val type: String,
    val hours: Int,
    val minutes: Int,
    val completedHours: Int = 0,
    val completedMinutes: Int = 0,
    val reminderEnabled: Boolean,
    val reminderTime: String
)