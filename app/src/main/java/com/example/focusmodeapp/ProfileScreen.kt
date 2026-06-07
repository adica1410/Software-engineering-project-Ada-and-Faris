package com.example.focusmodeapp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun ProfileScreen(
    onHomeClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onBlockedClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("focus_mode_user", Context.MODE_PRIVATE)

    val fullName = prefs.getString("fullName", "Focus User") ?: "Focus User"
    val email = prefs.getString("email", "user@example.com") ?: "user@example.com"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 42.dp, bottom = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Manage your personal focus profile.",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(34.dp))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF9B5CFF), Color(0xFF6F2CFF))
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = fullName.firstOrNull()?.uppercase() ?: "U",
                    color = Color.White,
                    fontSize = 46.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = fullName,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = email,
                color = Color(0xFFAAA6BB),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(34.dp))

            ProfileInfoCard("Name", fullName)

            Spacer(modifier = Modifier.height(14.dp))

            ProfileInfoCard("Email", email)

            Spacer(modifier = Modifier.height(28.dp))

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
                        onEditProfileClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit Profile",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .background(
                        Color(0xFF11162D),
                        RoundedCornerShape(18.dp)
                    )
                    .border(
                        0.8.dp,
                        Color(0xFF252B4C),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable {
                        onChangePasswordClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Change Password",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        AppBottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedScreen = "Profile",
            onHomeClick = onHomeClick,
            onStatisticsClick = onStatisticsClick,
            onGoalsClick = onGoalsClick,
            onProfileClick = onProfileClick,
            onBlockedClick = onBlockedClick
        )
    }
}

@Composable
fun ProfileInfoCard(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Text(
            text = label,
            color = Color(0xFFAAA6BB),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}