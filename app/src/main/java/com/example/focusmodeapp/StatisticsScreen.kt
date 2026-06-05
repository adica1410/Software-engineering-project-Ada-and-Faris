package com.example.focusmodeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen(
    onHomeClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences(
        "focus_mode_user",
        android.content.Context.MODE_PRIVATE
    )

    val userId = prefs.getInt("userId", 1)

    var sessions by remember { mutableStateOf<List<SessionResponse>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.api.getUserSessions(userId)
            if (response.isSuccessful) {
                sessions = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            sessions = emptyList()
        }
    }

    val totalMinutes = sessions.sumOf { it.duration_minutes }
    val totalSeconds = sessions.sumOf { it.duration_seconds }

    val weekSeconds = sessions
        .filter { isThisWeek(it.created_at) }
        .sumOf { it.duration_seconds }

    val dailySecondsForWeek = getDailySecondsForCurrentWeek(sessions)

    val lastWeekSeconds = sessions
        .filter { isLastWeek(it.created_at) }
        .sumOf { it.duration_seconds }

    val weekChangeText = calculateWeekChangeText(
        currentWeekSeconds = weekSeconds,
        lastWeekSeconds = lastWeekSeconds
    )

    val completedSessions = sessions.count { it.status == "completed" }

    val focusScore = calculateWeeklyFocusScore(weekSeconds)

    val currentStreak = calculateCurrentStreak(sessions)

    val todayMinutes = sessions
        .filter { isToday(it.created_at) }
        .sumOf { it.duration_minutes }

    val todaySeconds = sessions
        .filter { isToday(it.created_at) }
        .sumOf { it.duration_seconds }

    val todaySessions = sessions
        .count { isToday(it.created_at) }

    val weekMinutes = sessions
        .filter { isThisWeek(it.created_at) }
        .sumOf { it.duration_minutes }


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

            TotalStudyCard(totalSeconds, weekChangeText)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MiniStat("⏱", completedSessions.toString(), "Completed\nSessions", Modifier.weight(1f))
                MiniStat("🎯", "$focusScore%", "Weekly\nScore", Modifier.weight(1f))
                MiniStat("🔥", currentStreak.toString(), "Day\nStreak", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(18.dp))

            DailyStatisticsCard(
                todaySeconds = todaySeconds,
                todaySessions = todaySessions,
                dailySecondsForWeek = dailySecondsForWeek
            )

            Spacer(modifier = Modifier.height(18.dp))

            WeeklyOverviewCard(
                weekSeconds = weekSeconds,
                weekChangeText = weekChangeText,
                dailySecondsForWeek = dailySecondsForWeek
            )
        }

        AppBottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedScreen = "Statistics",
            onHomeClick = onHomeClick,
            onStatisticsClick = {},
            onGoalsClick = onGoalsClick,
            onProfileClick = onProfileClick
        )
    }
}

@Composable
fun TotalStudyCard(
    totalSeconds: Int,
    weekChangeText: String
) {
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

            Text(
                text = formatDuration(totalSeconds),
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = weekChangeText,
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
fun DailyStatisticsCard(
    todaySeconds: Int,
    todaySessions: Int,
    dailySecondsForWeek: List<Int>
) {
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

            SimpleBarChart(dailySecondsForWeek)

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
                    Text(formatDuration(todaySeconds), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Text("🎯", fontSize = 28.sp)

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text("Sessions", color = Color(0xFFAAA6BB), fontSize = 12.sp)
                    Text(todaySessions.toString(), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SimpleBarChart(dailySecondsForWeek: List<Int>) {
    val maxSeconds = dailySecondsForWeek.maxOrNull() ?: 0

    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        days.forEachIndexed { index, day ->
            val seconds = dailySecondsForWeek.getOrElse(index) { 0 }

            val height = if (maxSeconds > 0) {
                (seconds.toFloat() / maxSeconds.toFloat()).coerceAtLeast(0.12f)
            } else {
                0.12f
            }

            ChartBar(day, height)
        }
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
fun WeeklyOverviewCard(
    weekSeconds: Int,
    weekChangeText: String,
    dailySecondsForWeek: List<Int>
) {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val maxSeconds = dailySecondsForWeek.maxOrNull() ?: 0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
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
                    text = "This Week",
                    color = Color(0xFFB6B1C9),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .background(Color(0xFF12182D), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Study Time",
                color = Color(0xFFAAA6BB),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formatDuration(weekSeconds),
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = weekChangeText,
                color = Color(0xFF47E28A),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            days.forEachIndexed { index, day ->
                val seconds = dailySecondsForWeek.getOrElse(index) { 0 }

                WeeklyDayRow(
                    day = day,
                    seconds = seconds,
                    maxSeconds = maxSeconds
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}


@Composable
fun WeeklyDayRow(
    day: String,
    seconds: Int,
    maxSeconds: Int
) {
    val progress = if (maxSeconds > 0) {
        (seconds.toFloat() / maxSeconds.toFloat()).coerceAtLeast(0.03f)
    } else {
        0.03f
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day,
            color = Color(0xFFB6B1C9),
            fontSize = 12.sp,
            modifier = Modifier.width(36.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .background(Color(0xFF20263A), RoundedCornerShape(20.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF9B5CFF),
                                Color(0xFF6F2CFF)
                            )
                        ),
                        RoundedCornerShape(20.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = formatDuration(seconds),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.width(82.dp)
        )
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

fun parseBackendDate(dateString: String): Date? {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        formatter.parse(dateString)
    } catch (e: Exception) {
        null
    }
}

fun isToday(dateString: String?): Boolean {
    if (dateString == null) return false

    return try {
        val sdf =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )

        val date = sdf.parse(dateString)

        val sessionCal = Calendar.getInstance()
        sessionCal.time = date!!

        val todayCal = Calendar.getInstance()

        sessionCal.get(Calendar.YEAR) ==
                todayCal.get(Calendar.YEAR)
                &&
                sessionCal.get(Calendar.DAY_OF_YEAR) ==
                todayCal.get(Calendar.DAY_OF_YEAR)

    } catch (e: Exception) {
        false
    }
}

fun isThisWeek(dateString: String?): Boolean {
    if (dateString == null) return false

    return try {
        val sdf =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )

        val date = sdf.parse(dateString)

        val sessionCal = Calendar.getInstance()
        sessionCal.time = date!!

        val now = Calendar.getInstance()

        sessionCal.get(Calendar.YEAR) ==
                now.get(Calendar.YEAR)
                &&
                sessionCal.get(Calendar.WEEK_OF_YEAR) ==
                now.get(Calendar.WEEK_OF_YEAR)

    } catch (e: Exception) {
        false
    }
}


fun formatMinutes(totalMinutes: Int): String {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return "${hours}h ${minutes}m"
}

fun formatDuration(totalSeconds: Int): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return "${hours}h ${minutes}m ${seconds}s"
}


fun calculateCurrentStreak(sessions: List<SessionResponse>): Int {
    val completedSessionDates = sessions
        .filter { it.status == "completed" }
        .mapNotNull { session ->
            parseBackendDate(session.created_at)
        }
        .map { date ->
            val calendar = Calendar.getInstance()
            calendar.time = date

            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            calendar.timeInMillis
        }
        .toSet()

    if (completedSessionDates.isEmpty()) return 0

    val today = Calendar.getInstance()
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.set(Calendar.MILLISECOND, 0)

    var streak = 0
    val checkDay = today.clone() as Calendar

    while (completedSessionDates.contains(checkDay.timeInMillis)) {
        streak++
        checkDay.add(Calendar.DAY_OF_YEAR, -1)
    }

    return streak
}

fun calculateWeeklyFocusScore(
    weekSeconds: Int
): Int {

    val weekMinutes = weekSeconds / 60

    return when {
        weekMinutes >= 600 -> 100
        weekMinutes >= 480 -> 90
        weekMinutes >= 360 -> 80
        weekMinutes >= 240 -> 70
        weekMinutes >= 120 -> 60
        weekMinutes >= 60 -> 40
        weekMinutes >= 30 -> 20
        weekMinutes > 0 -> 10
        else -> 0
    }
}


fun isLastWeek(dateString: String?): Boolean {
    if (dateString == null) return false

    return try {
        val sdf = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )

        val date = sdf.parse(dateString)

        val sessionCal = Calendar.getInstance()
        sessionCal.time = date!!

        val lastWeekCal = Calendar.getInstance()
        lastWeekCal.add(Calendar.WEEK_OF_YEAR, -1)

        sessionCal.get(Calendar.YEAR) == lastWeekCal.get(Calendar.YEAR) &&
                sessionCal.get(Calendar.WEEK_OF_YEAR) == lastWeekCal.get(Calendar.WEEK_OF_YEAR)

    } catch (e: Exception) {
        false
    }
}



fun calculateWeekChangeText(
    currentWeekSeconds: Int,
    lastWeekSeconds: Int
): String {
    if (lastWeekSeconds == 0 && currentWeekSeconds == 0) {
        return "No study data yet"
    }

    if (lastWeekSeconds == 0 && currentWeekSeconds > 0) {
        return "↗ New activity this week"
    }

    val changePercent =
        (((currentWeekSeconds - lastWeekSeconds).toFloat() / lastWeekSeconds.toFloat()) * 100).toInt()

    return if (changePercent >= 0) {
        "↗ ${changePercent}% vs last week"
    } else {
        "↘ ${kotlin.math.abs(changePercent)}% vs last week"
    }
}


fun getDailySecondsForCurrentWeek(
    sessions: List<SessionResponse>
): List<Int> {
    val dailySeconds = MutableList(7) { 0 }

    sessions
        .filter { it.status == "completed" }
        .filter { isThisWeek(it.created_at) }
        .forEach { session ->
            val date = parseBackendDate(session.created_at)

            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date

                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

                val index = when (dayOfWeek) {
                    Calendar.MONDAY -> 0
                    Calendar.TUESDAY -> 1
                    Calendar.WEDNESDAY -> 2
                    Calendar.THURSDAY -> 3
                    Calendar.FRIDAY -> 4
                    Calendar.SATURDAY -> 5
                    Calendar.SUNDAY -> 6
                    else -> 0
                }

                dailySeconds[index] += session.duration_seconds
            }
        }

    return dailySeconds
}
