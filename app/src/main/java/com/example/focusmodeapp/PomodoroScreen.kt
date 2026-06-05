package com.example.focusmodeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PomodoroScreen(
    onBackClick: () -> Unit
) {
    val focusSeconds = 25 * 60
    val breakSeconds = 5 * 60

    var isFocusMode by remember { mutableStateOf(true) }
    var cycleCount by remember { mutableStateOf(1) }
    var timeLeft by remember { mutableStateOf(focusSeconds) }
    var isRunning by remember { mutableStateOf(false) }

    val totalTime = if (isFocusMode) focusSeconds else breakSeconds
    val progress = if (totalTime > 0) {
        1f - (timeLeft.toFloat() / totalTime.toFloat())
    } else {
        0f
    }

    LaunchedEffect(isRunning, timeLeft, isFocusMode) {
        if (isRunning && timeLeft > 0) {
            delay(1000)
            timeLeft--
        }

        if (isRunning && timeLeft == 0) {
            if (isFocusMode) {
                isFocusMode = false
                timeLeft = breakSeconds
            } else {
                isFocusMode = true
                cycleCount++
                timeLeft = focusSeconds
            }
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = "%02d:%02d".format(minutes, seconds)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 44.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "‹",
                color = Color.White,
                fontSize = 38.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Pomodoro Mode",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isFocusMode) "Focus session in progress" else "Break time — recharge",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(42.dp))

            Box(
                modifier = Modifier
                    .size(230.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                Color(0xFF9B5CFF).copy(alpha = 0.35f),
                                Color(0xFF11162D)
                            )
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isFocusMode) "Focus" else "Break",
                        color = Color(0xFF9B5CFF),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = formattedTime,
                        color = Color.White,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(30.dp)),
                color = Color(0xFF9B5CFF),
                trackColor = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PomodoroInfoCard(
                    title = "Cycle",
                    value = cycleCount.toString(),
                    modifier = Modifier.weight(1f)
                )

                PomodoroInfoCard(
                    title = "Mode",
                    value = if (isFocusMode) "Focus" else "Break",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(34.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PomodoroButton(
                    text = if (isRunning) "Pause" else "Start",
                    primary = true,
                    modifier = Modifier.weight(1f)
                ) {
                    isRunning = !isRunning
                }

                PomodoroButton(
                    text = "Reset",
                    primary = false,
                    modifier = Modifier.weight(1f)
                ) {
                    isRunning = false
                    isFocusMode = true
                    cycleCount = 1
                    timeLeft = focusSeconds
                }
            }
        }
    }
}

@Composable
fun PomodoroInfoCard(
    title: String,
    value: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .height(88.dp)
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(title, color = Color(0xFFAAA6BB), fontSize = 13.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PomodoroButton(
    text: String,
    primary: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(58.dp)
            .background(
                if (primary) {
                    Brush.horizontalGradient(
                        listOf(Color(0xFF9B5CFF), Color(0xFF6F2CFF))
                    )
                } else {
                    Brush.horizontalGradient(
                        listOf(Color(0xFF11162D), Color(0xFF11162D))
                    )
                },
                RoundedCornerShape(18.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}