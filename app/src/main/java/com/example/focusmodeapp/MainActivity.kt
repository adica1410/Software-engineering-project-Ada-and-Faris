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

            LaunchedEffect(Unit) {
                delay(3000)
                currentScreen = "welcome"
            }

            when (currentScreen) {

                "splash" -> {
                    SplashScreen()
                }

                "welcome" -> {
                    WelcomeScreen(
                        onCreateAccountClick = {
                            currentScreen = "createAccount"
                        }
                    )
                }

                "createAccount" -> {
                    CreateAccountScreen(
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

                            currentScreen = "welcome"
                        }
                    )
                }
            }
        }
    }
}