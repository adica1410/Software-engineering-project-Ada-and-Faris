package com.example.focusmodeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun GoalsScreen(
    savedGoals: List<StudyGoal>,
    onDeleteGoal: (StudyGoal) -> Unit,
    onEditGoal: (StudyGoal) -> Unit,
    onAddStudyTime: (StudyGoal) -> Unit,
    onHomeClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onCreateGoalClick: () -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val prefs = context.getSharedPreferences(
        "focus_mode_user",
        android.content.Context.MODE_PRIVATE
    )

    val userId = prefs.getInt("userId", 1)

    var databaseGoals by remember { mutableStateOf<List<StudyGoal>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.api.getUserGoals(userId)

            if (response.isSuccessful) {
                databaseGoals = response.body()?.map { goal ->
                    StudyGoal(
                        name = goal.title,
                        type = goal.goal_type,
                        hours = goal.target_minutes / 60,
                        minutes = goal.target_minutes % 60,
                        completedHours = goal.current_minutes / 60,
                        completedMinutes = goal.current_minutes % 60,
                        reminderEnabled = true,
                        reminderTime = "8:00 PM"
                    )
                } ?: emptyList()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to load goals", Toast.LENGTH_SHORT).show()
        }
    }

    val dailyGoal = databaseGoals.firstOrNull { it.type == "Daily" }
    val weeklyGoal = databaseGoals.firstOrNull { it.type == "Weekly" }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF020617), Color(0xFF040B25), Color(0xFF020617))
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(55.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Goals", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Set goals, stay consistent, achieve more.", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                    }

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF8438FF), Color(0xFFB56DFF))
                                )
                            )
                            .clickable { onCreateGoalClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                dailyGoal?.let {

                    GoalsDailyGoalCard(
                        goal = it,
                        onEditGoal = onEditGoal,
                        onAddStudyTime = onAddStudyTime,
                        onDeleteGoal = onDeleteGoal
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                }

                weeklyGoal?.let {

                    GoalsWeeklyGoalCard(
                        goal = it,
                        onEditGoal = onEditGoal,
                        onAddStudyTime = onAddStudyTime,
                        onDeleteGoal = onDeleteGoal
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                }

                GoalsMotivationCard()
            }
        }

        GoalsBottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selected = "Goals",
            onHomeClick = onHomeClick,
            onStatisticsClick = onStatisticsClick,
            onGoalsClick = onGoalsClick
        )
    }
}

@Composable
fun GoalsDailyGoalCard(
    goal: StudyGoal,
    onEditGoal: (StudyGoal) -> Unit,
    onAddStudyTime: (StudyGoal) -> Unit,
    onDeleteGoal: (StudyGoal) -> Unit
) {
    val context = LocalContext.current
    val targetMinutes = goal.hours * 60 + goal.minutes
    val completedMinutes = goal.completedHours * 60 + goal.completedMinutes
    val progressFloat = if (targetMinutes == 0) 0f else (completedMinutes.toFloat() / targetMinutes).coerceIn(0f, 1f)
    val progressPercent = (progressFloat * 100).toInt()
    val remainingMinutes = (targetMinutes - completedMinutes).coerceAtLeast(0)
    val remainingHours = remainingMinutes / 60
    val remainingMins = remainingMinutes % 60

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onDeleteGoal(goal)

                Toast.makeText(
                    context,
                    "Goal deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF081126)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(44.dp).background(Color(0xFF083A2B), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("☼", color = Color(0xFF4ADE80), fontSize = 24.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(goal.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Study every day", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF121A33))
                        .clickable { onEditGoal(goal) }
                        .padding(horizontal = 13.dp, vertical = 8.dp)
                ) {
                    Text("✎  Edit", color = Color(0xFF9B5CFF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(145.dp).border(10.dp, Color(0xFF4ADE80), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$progressPercent%", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                        Text("completed", color = Color(0xFF9CA3AF), fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.width(18.dp))

                Column {
                    Text("Today's Progress", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                    Text("$progressPercent%", color = Color(0xFF4ADE80), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)

                    Spacer(modifier = Modifier.height(12.dp))

                    GoalsInfo("Goal", formatGoalTime(goal.hours, goal.minutes))
                    GoalsInfo("Completed", formatGoalTime(goal.completedHours, goal.completedMinutes))
                    GoalsInfo("Remaining", formatGoalTime(remainingHours, remainingMins))
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            LinearProgressIndicator(
                progress = { progressFloat },
                modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(50)),
                color = Color(0xFF4ADE80),
                trackColor = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF123322))
                    .clickable { onAddStudyTime(goal) },
                contentAlignment = Alignment.Center
            ) {
                Text("+ Add 1h Study Time", color = Color(0xFF4ADE80), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun GoalsWeeklyGoalCard(
    goal: StudyGoal,
    onEditGoal: (StudyGoal) -> Unit,
    onAddStudyTime: (StudyGoal) -> Unit,
    onDeleteGoal: (StudyGoal) -> Unit
) {
    val targetMinutes = goal.hours * 60 + goal.minutes
    val completedMinutes = goal.completedHours * 60 + goal.completedMinutes
    val progressFloat = if (targetMinutes == 0) 0f else (completedMinutes.toFloat() / targetMinutes).coerceIn(0f, 1f)
    val progressPercent = (progressFloat * 100).toInt()
    val remainingMinutes = (targetMinutes - completedMinutes).coerceAtLeast(0)
    val remainingHours = remainingMinutes / 60
    val remainingMins = remainingMinutes % 60

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDeleteGoal(goal) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF081126)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier.size(44.dp).background(Color(0xFF1B1038), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("▣", color = Color(0xFF9B5CFF), fontSize = 24.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(goal.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Achieve focused study time", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .width(76.dp)
                        .height(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF121A33))
                        .clickable { onEditGoal(goal) },
                    contentAlignment = Alignment.Center
                ) {
                    Text("✎  Edit", color = Color(0xFF9B5CFF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("This Week's Progress", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                    Text("$progressPercent%", color = Color(0xFF9B5CFF), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(formatGoalTime(goal.completedHours, goal.completedMinutes), color = Color(0xFF9B5CFF), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("of ${formatGoalTime(goal.hours, goal.minutes)}", color = Color(0xFF9CA3AF), fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            LinearProgressIndicator(
                progress = { progressFloat },
                modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(50)),
                color = Color(0xFF9B5CFF),
                trackColor = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GoalsSmallStat("◷", "Remaining", formatGoalTime(remainingHours, remainingMins), Modifier.weight(1f))
                GoalsSmallStat("♢", "Reminder", if (goal.reminderEnabled) goal.reminderTime else "Off", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF171038))
                    .clickable { onAddStudyTime(goal) },
                contentAlignment = Alignment.Center
            ) {
                Text("+ Add 1h Study Time", color = Color(0xFFB56DFF), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun formatGoalTime(hours: Int, minutes: Int): String {
    return "${hours}h ${String.format("%02d", minutes)}m"
}

@Composable
fun GoalsInfo(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = Color(0xFF9CA3AF), fontSize = 12.sp)
        Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun GoalsSmallStat(icon: String, title: String, value: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF0E172E))
            .padding(15.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, color = Color(0xFF4ADE80), fontSize = 22.sp)
            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(title, color = Color(0xFF9CA3AF), fontSize = 12.sp)
                Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun GoalsMotivationCard() {
    Box(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Color(0xFF0E172E)).padding(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(44.dp).background(Color(0xFF1B1038), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("◎", color = Color(0xFF9B5CFF), fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text("Consistency is the key to mastery.", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Keep going and make it a habit!", color = Color(0xFF9CA3AF), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun GoalsBottomNavigationBar(
    modifier: Modifier = Modifier,
    selected: String,
    onHomeClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onGoalsClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().height(92.dp)
            .background(Color(0xFF0B1020), RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GoalsNavItem("⌂", "Home", selected == "Home", onHomeClick)
            GoalsNavItem("▥", "Statistics", selected == "Statistics", onStatisticsClick)
            GoalsNavItem("◎", "Goals", selected == "Goals", onGoalsClick)
            GoalsNavItem("⬡", "Blocked", false) {}
            GoalsNavItem("♙", "Profile", false) {}
        }
    }
}

@Composable
fun GoalsNavItem(
    icon: String,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp).clickable { onClick() }.padding(vertical = 6.dp)
    ) {
        Text(
            text = icon,
            color = if (selected) Color(0xFF9B5CFF) else Color(0xFF8B8FA3),
            fontSize = 27.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            color = if (selected) Color(0xFF9B5CFF) else Color(0xFF8B8FA3),
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}