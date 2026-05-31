package com.example.focusmodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.focusmodeapp.ui.CreateAccountScreen
import com.example.focusmodeapp.ui.SplashScreen
import com.example.focusmodeapp.ui.WelcomeScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("focus_mode_user", MODE_PRIVATE)

        setContent {

            var currentScreen by remember {
                mutableStateOf("splash")
            }

            var savedGoals by remember {
                mutableStateOf(
                    listOf(
                        StudyGoal(
                            name = "Daily Goal",
                            type = "Daily",
                            hours = 4,
                            minutes = 0,
                            completedHours = 0,
                            completedMinutes = 0,
                            reminderEnabled = true,
                            reminderTime = "8:00 PM"
                        ),
                        StudyGoal(
                            name = "Weekly Goal",
                            type = "Weekly",
                            hours = 15,
                            minutes = 0,
                            completedHours = 0,
                            completedMinutes = 0,
                            reminderEnabled = true,
                            reminderTime = "8:00 PM"
                        )
                    )
                )
            }

            var editingGoal by remember {
                mutableStateOf<StudyGoal?>(null)
            }

            LaunchedEffect(Unit) {
                delay(3000)
                currentScreen = "welcome"
            }

            when (currentScreen) {

                "splash" -> SplashScreen()

                "welcome" -> WelcomeScreen(
                    onCreateAccountClick = {
                        currentScreen = "createAccount"
                    },
                    onLoginClick = {
                        currentScreen = "login"
                    },
                    onSkipClick = {
                        currentScreen = "home"
                    }
                )

                "createAccount" -> CreateAccountScreen(
                    onBackClick = {
                        currentScreen = "welcome"
                    },
                    onAccountCreated = { fullName, email, password ->
                        prefs.edit()
                            .putString("fullName", fullName)
                            .putString("email", email)
                            .putString("password", password)
                            .putBoolean("isRegistered", true)
                            .apply()

                        currentScreen = "login"
                    }
                )

                "login" -> LoginScreen(
                    savedEmail = prefs.getString("email", null),
                    savedPassword = prefs.getString("password", null),
                    onLoginSuccess = { userId, fullName, email ->
                        prefs.edit()
                            .putInt("userId", userId)
                            .putString("fullName", fullName)
                            .putString("email", email)
                            .putBoolean("isLoggedIn", true)
                            .apply()

                        currentScreen = "home"
                    },
                    onBackClick = {
                        currentScreen = "welcome"
                    }
                )

                "home" -> HomeScreen(
                    onStartSessionClick = {
                        currentScreen = "startSession"
                    },
                    onStatisticsClick = {
                        currentScreen = "statistics"
                    },
                    onGoalsClick = {
                        currentScreen = "goals"
                    },
                    onBadgesClick = {
                        currentScreen = "badges"
                    },
                    onHistoryClick = {
                        currentScreen = "history"
                    }
                )

                "statistics" -> StatisticsScreen(
                    onHomeClick = {
                        currentScreen = "home"
                    },
                    onGoalsClick = {
                        currentScreen = "goals"
                    }
                )

                "goals" -> GoalsScreen(
                    savedGoals = savedGoals,

                    onDeleteGoal = { goal ->
                        savedGoals = savedGoals - goal
                    },

                    onEditGoal = { goal ->
                        editingGoal = goal
                        currentScreen = "createGoal"
                    },

                    onAddStudyTime = { goal ->
                        val exists = savedGoals.any { it == goal }

                        savedGoals = if (exists) {
                            savedGoals.map {
                                if (it == goal) {
                                    it.copy(
                                        completedHours = it.completedHours + 1
                                    )
                                } else {
                                    it
                                }
                            }
                        } else {
                            savedGoals + goal.copy(
                                completedHours = goal.completedHours + 1
                            )
                        }
                    },

                    onHomeClick = {
                        currentScreen = "home"
                    },

                    onStatisticsClick = {
                        currentScreen = "statistics"
                    },

                    onGoalsClick = {},

                    onCreateGoalClick = {
                        editingGoal = null
                        currentScreen = "createGoal"
                    }
                )

                "badges" -> BadgesScreen(
                    onBackClick = {
                        currentScreen = "home"
                    }
                )

                "history" -> SessionHistoryScreen(
                    onBackClick = {
                        currentScreen = "home"
                    }
                )


                "createGoal" -> CreateGoalScreen(
                    goalToEdit = editingGoal,

                    onBackClick = {
                        editingGoal = null
                        currentScreen = "goals"
                    },

                    onSaveGoalClick = { goal ->

                        if (editingGoal != null) {
                            val alreadyExists = savedGoals.any { it == editingGoal }

                            savedGoals = if (alreadyExists) {
                                savedGoals.map {
                                    if (it == editingGoal) goal else it
                                }
                            } else {
                                savedGoals + goal
                            }
                        } else {
                            savedGoals = savedGoals + goal
                        }

                        editingGoal = null
                        currentScreen = "goals"
                    }
                )

                "startSession" -> StartFocusSessionScreen(
                    onBackClick = {
                        currentScreen = "home"
                    }
                )
            }
        }
    }
}