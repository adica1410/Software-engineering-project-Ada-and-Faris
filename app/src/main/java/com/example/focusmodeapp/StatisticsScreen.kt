package com.example.focusmodeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen(
    onHomeClick: () -> Unit,
    onGoalsClick: () -> Unit
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
                .padding(horizontal = 20.dp)
                .padding(top = 38.dp, bottom = 110.dp)
        ) {

            Text(
                text = "Statistics",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Track your progress and build consistency.",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            TotalStudyCard()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MiniStat("⏱", "28", "Completed\nSessions", Modifier.weight(1f))
                MiniStat("🎯", "92%", "Focus\nScore", Modifier.weight(1f))
                MiniStat("🔥", "7", "Day\nStreak", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(18.dp))

            DailyStatisticsCard()

            Spacer(modifier = Modifier.height(18.dp))

            WeeklyOverviewCard()
        }

        StatisticsBottomNav(
            modifier = Modifier.align(Alignment.BottomCenter),
            onHomeClick = onHomeClick,
            onGoalsClick = onGoalsClick
        )
    }
}

@Composable
fun TotalStudyCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF5728C9),
                        Color(0xFF2C1369)
                    )
                ),
                RoundedCornerShape(18.dp)
            )
            .padding(22.dp)
    ) {
        Column {
            Text(
                text = "Total Study Time",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "42",
                    color = Color.White,
                    fontSize = 39.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "h ",
                    color = Color.White,
                    fontSize = 25.sp
                )

                Text(
                    text = "37",
                    color = Color.White,
                    fontSize = 39.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "m",
                    color = Color.White,
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "↗ 12% vs last week",
                color = Color(0xFF47E28A),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "◷",
            color = Color(0xFFE1D2FF),
            fontSize = 70.sp,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun MiniStat(
    icon: String,
    number: String,
    label: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(86.dp)
            .background(Color(0xFF10162A), RoundedCornerShape(16.dp))
            .border(0.8.dp, Color(0xFF232A45), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 28.sp)

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = number,
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = label,
                    color = Color(0xFFAAA6BB),
                    fontSize = 10.sp,
                    lineHeight = 13.sp
                )
            }
        }
    }
}

@Composable
fun DailyStatisticsCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(335.dp)
            .background(Color(0xFF0D1326), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF222A45), RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Daily Statistics",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "View All",
                    color = Color(0xFF9B5CFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "● Study Time (h)    ● Sessions",
                color = Color(0xFFB6B1C9),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            SimpleBarChart()

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color(0xFF12182D), RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("◷", color = Color(0xFF9B5CFF), fontSize = 31.sp)

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Today", color = Color(0xFFAAA6BB), fontSize = 12.sp)
                    Text("4h 25m", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Text("🎯", fontSize = 28.sp)

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text("Sessions", color = Color(0xFFAAA6BB), fontSize = 12.sp)
                    Text("5", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SimpleBarChart() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        ChartBar("Mon", 0.45f)
        ChartBar("Tue", 0.70f)
        ChartBar("Wed", 0.92f)
        ChartBar("Thu", 0.50f)
        ChartBar("Fri", 0.72f)
        ChartBar("Sat", 0.40f)
        ChartBar("Sun", 0.82f)
    }
}

@Composable
fun ChartBar(day: String, height: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(22.dp)
                .height((100 * height).dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF9B5CFF), Color(0xFF5525C8))
                    ),
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = day,
            color = Color(0xFFAAA6BB),
            fontSize = 11.sp
        )
    }
}

@Composable
fun WeeklyOverviewCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(Color(0xFF0D1326), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF222A45), RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Weekly Overview",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "This Week  ⌄",
                    color = Color(0xFFB6B1C9),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .background(Color(0xFF12182D), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Study Time",
                color = Color(0xFFAAA6BB),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "24h 18m",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "↗ 18% vs last week",
                color = Color(0xFF47E28A),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Mon     Tue     Wed     Thu     Fri     Sat     Sun",
                color = Color(0xFFAAA6BB),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun StatisticsBottomNav(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onGoalsClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp)
            .background(
                Color(0xFF0B1020),
                RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItemStats("⌂", "Home", false, onClick = onHomeClick)
            BottomNavItemStats("▥", "Statistics", true, onClick = {})
            BottomNavItemStats("◎", "Goals", false, onClick = onGoalsClick)
            BottomNavItemStats("⬡", "Blocked", false, onClick = {})
            BottomNavItemStats("♙", "Profile", false, onClick = {})
        }
    }
}
@Composable
fun BottomNavItemStats(
    icon: String,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
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