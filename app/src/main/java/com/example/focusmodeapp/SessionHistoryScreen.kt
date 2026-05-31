package com.example.focusmodeapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun SessionHistoryScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val prefs = context.getSharedPreferences("focus_mode_user", Context.MODE_PRIVATE)
    val userId = prefs.getInt("userId", 1)

    var sessions by remember { mutableStateOf<List<SessionResponse>>(emptyList()) }

    fun loadSessions() {
        scope.launch {
            try {
                val response = RetrofitClient.api.getUserSessions(userId)
                if (response.isSuccessful) {
                    sessions = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load sessions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        loadSessions()
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
                .padding(top = 34.dp)
        ) {
            Text(
                text = "‹",
                color = Color.White,
                fontSize = 34.sp,
                modifier = Modifier.clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Session History",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Review your completed focus sessions.",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(sessions) { session ->
                    SessionHistoryCard(
                        session = session,
                        onDeleteClick = {
                            scope.launch {
                                try {
                                    val response = RetrofitClient.api.deleteSession(session.id)
                                    if (response.isSuccessful) {
                                        Toast.makeText(context, "Session deleted", Toast.LENGTH_SHORT).show()
                                        loadSessions()
                                    } else {
                                        Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Backend error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SessionHistoryCard(
    session: SessionResponse,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Focus Session",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${formatDuration(session.duration_seconds)} • ${session.status}",
                    color = Color(0xFFAAA6BB),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = session.created_at.take(10),
                    color = Color(0xFF777B91),
                    fontSize = 12.sp
                )
            }

            Text(
                text = "Delete",
                color = Color(0xFFFF4D6D),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onDeleteClick() }
            )
        }
    }
}