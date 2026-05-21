package com.example.focusmodeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onStartSessionClick: () -> Unit,
    onStatisticsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(top = 28.dp, bottom = 100.dp)
        ) {

            HomeTopBar()

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Good Evening, ",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Row {
                Text(
                    text = "Ada! ",
                    color = Color(0xFF9B5CFF),
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "👋",
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Stay focused and achieve your goals.",
                color = Color(0xFFB6B1C9),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            FocusMainCard(
                onStartSessionClick = onStartSessionClick
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Quick Stats",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "View all  ›",
                    color = Color(0xFF9B5CFF),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                    icon = "▣",
                    title = "Today",
                    value = "2h 15m",
                    subtitle = "Study Time",
                    color = Color(0xFF9B5CFF),
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    icon = "▥",
                    title = "This Week",
                    value = "12h 0m",
                    subtitle = "Study Time",
                    color = Color(0xFF36D979),
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    icon = "◷",
                    title = "Total",
                    value = "84h 30m",
                    subtitle = "All Time",
                    color = Color(0xFF3D8BFF),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            DailyGoalCard()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                PomodoroCard(Modifier.weight(1f))
                StreakCard(Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ReminderCard()
        }

        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onStatisticsClick = onStatisticsClick
        )
    }
}

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "☰",
            color = Color.White,
            fontSize = 30.sp
        )

        Box {
            Text(
                text = "♧",
                color = Color.White,
                fontSize = 31.sp
            )

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color(0xFF8B3DFF), CircleShape)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun FocusMainCard(
    onStartSessionClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(490.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF191154),
                        Color(0xFF090C1C)
                    )
                ),
                RoundedCornerShape(22.dp)
            )
            .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(22.dp))
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF9B5CFF).copy(alpha = 0.18f),
                        Color.Transparent
                    )
                ),
                radius = size.width * 0.48f,
                center = Offset(size.width / 2f, size.height * 0.40f)
            )


            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFB56DFF),
                        Color(0xFF7A2BFF)
                    )
                ),
                radius = size.width * 0.28f,
                center = Offset(size.width / 2f, size.height * 0.40f),
                style = Stroke(width = 12.dp.toPx())
            )


            drawCircle(
                color = Color(0xFFE9D9FF),
                radius = 10.dp.toPx(),
                center = Offset(size.width / 2f, size.height * 0.12f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "◎  Focus Session",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFF2B1B5E), RoundedCornerShape(10.dp))
                    .padding(horizontal = 13.dp, vertical = 9.dp)
            )

            Text(
                text = "☷",
                color = Color.White,
                fontSize = 28.sp
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-13).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "⌁",
                color = Color(0xFF9B5CFF),
                fontSize = 26.sp
            )

            Text(
                text = "Focus Mode",
                color = Color(0xFF9B5CFF),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "25:00",
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Set duration  ›",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFF241D4A), RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TimerButton(
                icon = "Ⅱ",
                label = "Pause",
                active = false,
                iconColor = Color.White
            )

            TimerButton(
                icon = "▶",
                label = "Start",
                active = true,
                iconColor = Color.White,
                onStartSessionClick = onStartSessionClick
            )

            TimerButton(
                icon = "□",
                label = "Stop",
                active = false,
                iconColor = Color(0xFFFF4D6D)
            )
        }
    }
}

@Composable
fun TimerButton(
    icon: String,
    label: String,
    active: Boolean,
    iconColor: Color,
    onStartSessionClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(if (active) 84.dp else 74.dp)
                .clickable {
                    if (label == "Start") {
                        onStartSessionClick()
                    }
                }
                .background(
                    if (active) {
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF9B5CFF),
                                Color(0xFF6F2CFF)
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF151A2E),
                                Color(0xFF111525)
                            )
                        )
                    },
                    RoundedCornerShape(22.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF2A304C),
                    RoundedCornerShape(22.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = icon,
                color = iconColor,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun StatCard(
    icon: String,
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(120.dp)
            .background(Color(0xFF11162D), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column {
            Text(
                text = icon,
                color = color,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = value,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = subtitle,
                color = Color(0xFFAAA6BB),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun DailyGoalCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text("🎯", fontSize = 34.sp)

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Daily Goal",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Study consistently every day",
                            color = Color(0xFFAAA6BB),
                            fontSize = 13.sp
                        )
                    }
                }

                Text(
                    text = "80%",
                    color = Color(0xFF36D979),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF2A3040))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF36D979))
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "4h 0m / 5h 0m",
                    color = Color(0xFF36D979),
                    fontSize = 13.sp
                )

                Text(
                    text = "Edit Goal  ›",
                    color = Color(0xFF9B5CFF),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun PomodoroCard(modifier: Modifier) {
    Box(
        modifier = modifier
            .height(160.dp)
            .background(Color(0xFF251020), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Text("🍅", fontSize = 33.sp)

            Text(
                text = "Pomodoro Mode",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "25m focus / 5m break",
                color = Color(0xFFAAA6BB),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .border(1.dp, Color(0xFFFF4D6D), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Start Pomodoro",
                    color = Color(0xFFFF4D6D),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StreakCard(modifier: Modifier) {
    Box(
        modifier = modifier
            .height(160.dp)
            .background(Color(0xFF17151D), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Text("🔥", fontSize = 33.sp)

            Text(
                text = "Current Streak",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Keep it up!",
                color = Color(0xFFAAA6BB),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "7",
                color = Color(0xFFFFA23D),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "days",
                color = Color(0xFFAAA6BB),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ReminderCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(Color(0xFF151044), RoundedCornerShape(17.dp))
            .padding(15.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🔔", fontSize = 34.sp)

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Upcoming Reminder",
                    color = Color(0xFFAAA6BB),
                    fontSize = 13.sp
                )

                Text(
                    text = "Math Study",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Today, 18:00",
                    color = Color(0xFFAAA6BB),
                    fontSize = 12.sp
                )
            }

            Text(
                text = "View All",
                color = Color(0xFFB56DFF),
                fontSize = 14.sp,
                modifier = Modifier
                    .background(Color(0xFF27145F), RoundedCornerShape(12.dp))
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    onStatisticsClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(86.dp)
            .background(
                Color(0xFF0B1020),
                RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
            )
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BottomNavItem("⌂", "Home", true)
            BottomNavItem("▥", "Statistics", false, onClick = onStatisticsClick)
            BottomNavItem("◎", "Goals", false)
            BottomNavItem("⬡", "Blocked", false)
            BottomNavItem("♙", "Profile", false)
        }
    }
}

@Composable
fun BottomNavItem(
    icon: String,
    title: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = icon,
            color = if (selected) Color(0xFF9B5CFF) else Color(0xFF8B8FA3),
            fontSize = 27.sp
        )

        Text(
            text = title,
            color = if (selected) Color(0xFF9B5CFF) else Color(0xFF8B8FA3),
            fontSize = 12.sp
        )
    }
}