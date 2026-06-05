package com.example.focusmodeapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
fun BlockedWebsitesScreen(
    onHomeClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onBlockedClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("focus_mode_user", Context.MODE_PRIVATE)

    var websiteInput by remember { mutableStateOf("") }

    var blockedWebsites by remember {
        mutableStateOf(
            prefs.getString("blocked_websites", "instagram.com,tiktok.com,youtube.com")
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.isNotBlank() }
                ?: emptyList()
        )
    }

    fun saveBlockedWebsites(newList: List<String>) {
        blockedWebsites = newList
        prefs.edit()
            .putString("blocked_websites", newList.joinToString(","))
            .apply()
    }

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
                .padding(top = 42.dp, bottom = 120.dp)
        ) {
            Text(
                text = "Blocked Websites",
                color = Color.White,
                fontSize = 31.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Manage websites that distract you during focus sessions.",
                color = Color(0xFFB6B1C9),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(26.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF11162D), RoundedCornerShape(20.dp))
                    .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        text = "Add website",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Example: instagram.com, tiktok.com, youtube.com",
                        color = Color(0xFFAAA6BB),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BasicTextField(
                        value = websiteInput,
                        onValueChange = { websiteInput = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color(0xFF0B1020), RoundedCornerShape(16.dp))
                            .border(0.8.dp, Color(0xFF2B3355), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 17.dp),
                        decorationBox = { innerTextField ->
                            if (websiteInput.isBlank()) {
                                Text(
                                    text = "Enter website URL",
                                    color = Color(0xFF6F7488),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF9B5CFF), Color(0xFF6F2CFF))
                                ),
                                RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                val cleanWebsite = websiteInput
                                    .trim()
                                    .lowercase()
                                    .removePrefix("https://")
                                    .removePrefix("http://")
                                    .removePrefix("www.")
                                    .substringBefore("/")

                                when {
                                    cleanWebsite.isBlank() -> {
                                        Toast.makeText(context, "Please enter a website", Toast.LENGTH_SHORT).show()
                                    }

                                    blockedWebsites.contains(cleanWebsite) -> {
                                        Toast.makeText(context, "Website already blocked", Toast.LENGTH_SHORT).show()
                                    }

                                    else -> {
                                        saveBlockedWebsites(blockedWebsites + cleanWebsite)
                                        websiteInput = ""
                                        Toast.makeText(context, "Website blocked", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+ Add Website",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Blocked List",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (blockedWebsites.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
                        .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(18.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No blocked websites yet.",
                        color = Color(0xFFAAA6BB),
                        fontSize = 15.sp
                    )
                }
            } else {
                blockedWebsites.forEach { website ->
                    BlockedWebsiteItem(
                        website = website,
                        onRemoveClick = {
                            saveBlockedWebsites(blockedWebsites - website)
                            Toast.makeText(context, "Website removed", Toast.LENGTH_SHORT).show()
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        AppBottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedScreen = "Blocked",
            onHomeClick = onHomeClick,
            onStatisticsClick = onStatisticsClick,
            onGoalsClick = onGoalsClick,
            onBlockedClick = onBlockedClick,
            onProfileClick = onProfileClick
        )
    }
}

@Composable
fun BlockedWebsiteItem(
    website: String,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(Color(0xFF1B1038), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "⛔",
                fontSize = 21.sp
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = website,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Blocked during focus mode",
                color = Color(0xFFAAA6BB),
                fontSize = 12.sp
            )
        }

        Box(
            modifier = Modifier
                .background(Color(0xFF2A1020), RoundedCornerShape(12.dp))
                .clickable { onRemoveClick() }
                .padding(horizontal = 14.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Remove",
                color = Color(0xFFFF4D6D),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

