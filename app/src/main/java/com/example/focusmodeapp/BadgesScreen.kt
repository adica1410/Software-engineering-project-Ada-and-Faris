package com.example.focusmodeapp

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BadgesScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("focus_mode_user", Context.MODE_PRIVATE)
    val userId = prefs.getInt("userId", 1)

    var badges by remember { mutableStateOf<List<BadgeResponse>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.api.getUserBadges(userId)
            if (response.isSuccessful) {
                badges = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            badges = emptyList()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
                .padding(top = 34.dp, bottom = 24.dp)
        ) {
            Text(
                text = "‹",
                color = Color.White,
                fontSize = 34.sp,
                modifier = Modifier.clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Achievements",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your earned rewards based on real study progress.",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(badges) { badge ->
                    BadgeCard(badge)
                }
            }
        }
    }
}

@Composable
fun BadgeCard(badge: BadgeResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF9B5CFF), Color(0xFF6F2CFF))
                        ),
                        RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🏆",
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = badge.badge_name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = badge.badge_description,
                    color = Color(0xFFAAA6BB),
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}