package com.example.focusmodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.focusmodeapp.ui.CreateAccountScreen
import com.example.focusmodeapp.ui.SplashScreen
import com.example.focusmodeapp.ui.WelcomeScreen
import kotlinx.coroutines.delay
import com.example.focusmodeapp.StartFocusSessionScreen
import com.example.focusmodeapp.StatisticsScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("focus_mode_user", MODE_PRIVATE)

        setContent {
            var currentScreen by remember { mutableStateOf("splash") }

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
                    onLoginSuccess = {
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
                    }
                )

                "statistics" -> StatisticsScreen(
                    onHomeClick = {
                        currentScreen = "home"
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