package com.example.focusmodeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartFocusSessionScreen(
    onBackClick: () -> Unit
) {
    var selectedDuration by remember { mutableStateOf(25) }
    var focusMode by remember { mutableStateOf(true) }
    var blockDistractions by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 32.dp, bottom = 100.dp)
    ) {

        Text(
            text = "‹",
            color = Color.White,
            fontSize = 34.sp,
            modifier = Modifier.clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Start Focus Session",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Choose your settings and eliminate distractions.",
                    color = Color(0xFFB5ADC8),
                    fontSize = 15.sp
                )
            }

            Text(
                text = "ϟ  Focus Mode",
                color = Color(0xFF9B5CFF),
                fontSize = 14.sp,
                modifier = Modifier
                    .border(1.dp, Color(0xFF5126A8), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 9.dp)
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        SettingCard {
            Text(
                text = "◷   1. Choose Duration",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(15, 25, 45, 60, 90).forEach { minutes ->
                    DurationBox(
                        minutes = minutes,
                        selected = selectedDuration == minutes,
                        onClick = { selectedDuration = minutes },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Custom Duration", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("0 hr   $selectedDuration min", color = Color(0xFF9B5CFF), fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(Color(0xFF20273A), RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(selectedDuration / 180f)
                        .height(6.dp)
                        .background(Color(0xFF8B3DFF), RoundedCornerShape(10.dp))
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("5 min", color = Color(0xFFAAA6BB), fontSize = 13.sp)
                Text("30 min", color = Color(0xFFAAA6BB), fontSize = 13.sp)
                Text("180 min", color = Color(0xFFAAA6BB), fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SettingCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "◉   2. Focus Mode",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Stay focused. Pause notifications and\nenter deep focus.",
                        color = Color(0xFFB5ADC8),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }

                Switch(
                    checked = focusMode,
                    onCheckedChange = { focusMode = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF7B38FF)
                    )
                )
            }

            InfoBox("✦", "Focus Mode will pause notifications and\nhelp you concentrate better.")
        }

        Spacer(modifier = Modifier.height(18.dp))

        SettingCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "⬡   3. Block Distractions",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Block distracting websites and apps\nduring this session.",
                        color = Color(0xFFB5ADC8),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }

                Switch(
                    checked = blockDistractions,
                    onCheckedChange = { blockDistractions = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF7B38FF)
                    )
                )
            }

            InfoBox("⬡", "Your blocked websites and apps will be\nrestricted until the session ends.")
        }

        Spacer(modifier = Modifier.height(26.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF8438FF), Color(0xFF732EF0))
                    ),
                    RoundedCornerShape(13.dp)
                )
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Start Session",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "▷",
                color = Color.White,
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 22.dp)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(1.dp, Color(0xFF202A45), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "◷   You're about to start a $selectedDuration minute focus session.",
                color = Color(0xFFB5ADC8),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SettingCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E1328), RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFF222A45), RoundedCornerShape(18.dp))
            .padding(18.dp),
        content = content
    )
}

@Composable
fun DurationBox(
    minutes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(70.dp)
            .background(
                if (selected) {
                    Brush.verticalGradient(
                        listOf(Color(0xFF9B5CFF), Color(0xFF6428D9))
                    )
                } else {
                    Brush.verticalGradient(
                        listOf(Color(0xFF11172A), Color(0xFF0D1223))
                    )
                },
                RoundedCornerShape(13.dp)
            )
            .border(1.dp, Color(0xFF252D48), RoundedCornerShape(13.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$minutes",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "min",
                color = Color(0xFFD5D0E8),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun InfoBox(icon: String, text: String) {
    Spacer(modifier = Modifier.height(18.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF11182D), RoundedCornerShape(13.dp))
            .border(1.dp, Color(0xFF202A45), RoundedCornerShape(13.dp))
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            color = Color(0xFF9B5CFF),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            color = Color(0xFFB5ADC8),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}