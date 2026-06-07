package com.example.focusmodeapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    onPasswordChanged: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("focus_mode_user", Context.MODE_PRIVATE)

    val scope = rememberCoroutineScope()
    val userId = prefs.getInt("userId", 1)

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val savedPassword = prefs.getString("password", "") ?: ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 44.dp)
        ) {
            Text(
                text = "‹",
                color = Color.White,
                fontSize = 36.sp,
                modifier = Modifier.clickable {
                    onBackClick()
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Change Password",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Update your password to keep your account secure.",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(34.dp))

            PasswordInputCard(
                label = "Current Password",
                value = currentPassword,
                onValueChange = { currentPassword = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            PasswordInputCard(
                label = "New Password",
                value = newPassword,
                onValueChange = { newPassword = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            PasswordInputCard(
                label = "Confirm Password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it }
            )

            Spacer(modifier = Modifier.height(34.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF9B5CFF), Color(0xFF6F2CFF))
                        ),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable {
                        when {
                            currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank() -> {
                                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                            }

                            newPassword != confirmPassword -> {
                                Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
                            }

                            newPassword.length < 6 -> {
                                Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                            }

                            else -> {
                                scope.launch {
                                    try {
                                        val response = RetrofitClient.api.changePassword(
                                            userId,
                                            ChangePasswordRequest(
                                                current_password = currentPassword,
                                                new_password = newPassword
                                            )
                                        )

                                        if (response.isSuccessful) {
                                            prefs.edit()
                                                .putString("password", newPassword)
                                                .apply()

                                            Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                                            onPasswordChanged()
                                        } else {
                                            Toast.makeText(context, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Backend error: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save Password",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PasswordInputCard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .background(
                    Color(0xFF11162D),
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 18.dp, vertical = 18.dp)
        )
    }
}