package com.example.focusmodeapp

import android.content.Context
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

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences(
        "focus_mode_user",
        Context.MODE_PRIVATE
    )

    var fullName by remember {
        mutableStateOf(
            prefs.getString("fullName", "") ?: ""
        )
    }

    var email by remember {
        mutableStateOf(
            prefs.getString("email", "") ?: ""
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Edit Profile",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Update your profile information.",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Name",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF11162D),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(18.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Email",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF11162D),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(18.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF9B5CFF),
                                Color(0xFF6F2CFF)
                            )
                        ),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable {

                        prefs.edit()
                            .putString("fullName", fullName)
                            .putString("email", email)
                            .apply()

                        onSaveClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save Changes",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .background(
                        Color(0xFF11162D),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable {
                        onBackClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}