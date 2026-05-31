package com.example.focusmodeapp

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import android.widget.Toast
import kotlinx.coroutines.launch

@Composable
fun CreateGoalScreen(
    goalToEdit: StudyGoal? = null,
    onBackClick: () -> Unit,
    onSaveGoalClick: (StudyGoal) -> Unit
) {
    var selectedTab by remember { mutableStateOf(goalToEdit?.type ?: "Daily") }
    var hours by remember { mutableStateOf(goalToEdit?.hours?.toFloat() ?: 3f) }
    var minutes by remember { mutableStateOf(goalToEdit?.minutes?.toFloat() ?: 0f) }
    var goalName by remember { mutableStateOf(goalToEdit?.name ?: "") }
    var reminderEnabled by remember { mutableStateOf(goalToEdit?.reminderEnabled ?: true) }
    var selectedTime by remember { mutableStateOf(goalToEdit?.reminderTime ?: "8:00 PM") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val prefs = context.getSharedPreferences(
        "focus_mode_user",
        android.content.Context.MODE_PRIVATE
    )

    val userId = prefs.getInt("userId", 1)
    var isSaving by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020817))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp)
            .padding(top = 28.dp, bottom = 28.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "‹",
                color = Color.White,
                fontSize = 34.sp,
                modifier = Modifier.clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (goalToEdit == null) "Create Goal" else "Edit Goal",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(28.dp))
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (goalToEdit == null)
                "Set a goal to stay focused and consistent."
            else
                "Update your goal and keep your progress clear.",
            color = Color(0xFF9CA3AF),
            fontSize = 13.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(Color(0xFF0D1328))
                .padding(5.dp)
        ) {
            GoalTab("☼  Daily Goal", selectedTab == "Daily", Modifier.weight(1f)) {
                selectedTab = "Daily"
            }

            GoalTab("▣  Weekly Goal", selectedTab == "Weekly", Modifier.weight(1f)) {
                selectedTab = "Weekly"
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        SectionTitle("Target Time", "Set how much time you want to focus.")

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111034), RoundedCornerShape(15.dp))
                .padding(18.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TimeBox(String.format("%02d", hours.toInt()), "Hours")

                    Text(
                        text = ":",
                        color = Color(0xFF9B5CFF),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )

                    TimeBox(String.format("%02d", minutes.toInt()), "Minutes")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Hours",
                    color = Color(0xFF9CA3AF),
                    fontSize = 12.sp
                )

                Slider(
                    value = hours,
                    onValueChange = { hours = it },
                    valueRange = 0f..12f,
                    steps = 11,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFB56DFF),
                        activeTrackColor = Color(0xFF8438FF),
                        inactiveTrackColor = Color(0xFF20263D)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("0h", color = Color(0xFF9CA3AF), fontSize = 12.sp)
                    Text("12h", color = Color(0xFF9CA3AF), fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Minutes",
                    color = Color(0xFF9CA3AF),
                    fontSize = 12.sp
                )

                Slider(
                    value = minutes,
                    onValueChange = { minutes = it },
                    valueRange = 0f..59f,
                    steps = 58,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFB56DFF),
                        activeTrackColor = Color(0xFF8438FF),
                        inactiveTrackColor = Color(0xFF20263D)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("0m", color = Color(0xFF9CA3AF), fontSize = 12.sp)
                    Text("59m", color = Color(0xFF9CA3AF), fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        SectionTitle("Goal Name", "Give your goal a name to keep you motivated.")

        Spacer(modifier = Modifier.height(8.dp))

        InputGoalBox(
            value = goalName,
            onValueChange = { goalName = it },
            hint = "e.g. Study 3h 30m Every Day"
        )

        Spacer(modifier = Modifier.height(22.dp))

        SectionTitle("Reminder", "Get a daily reminder to stay on track.")

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .background(Color(0xFF0D1328), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("♢", color = Color(0xFF9B5CFF), fontSize = 26.sp)

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Daily Reminder",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Choose a time for your reminder",
                        color = Color(0xFF9CA3AF),
                        fontSize = 11.sp
                    )
                }

                Switch(
                    checked = reminderEnabled,
                    onCheckedChange = { reminderEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF7B38FF)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF0D1328), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Time",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "$selectedTime ▼",
                color = Color(0xFF9B5CFF),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .background(Color(0xFF171038), RoundedCornerShape(10.dp))
                    .clickable {
                        TimePickerDialog(
                            context,
                            { _: TimePicker, hour: Int, minute: Int ->
                                val amPm = if (hour >= 12) "PM" else "AM"

                                val formattedHour =
                                    when {
                                        hour == 0 -> 12
                                        hour > 12 -> hour - 12
                                        else -> hour
                                    }

                                selectedTime = String.format(
                                    "%d:%02d %s",
                                    formattedHour,
                                    minute,
                                    amPm
                                )
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                        ).show()
                    }
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF5126A8), RoundedCornerShape(13.dp))
                .background(Color(0xFF090E20), RoundedCornerShape(13.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("✦", color = Color(0xFF9B5CFF), fontSize = 28.sp)

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (goalToEdit == null) "Why set goals?" else "Editing your goal",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = if (goalToEdit == null)
                            "Goals help you stay consistent and track your\nprogress over time."
                        else
                            "Change your target, name, reminder or time\nand save the updated version.",
                        color = Color(0xFFB5ADC8),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF8438FF),
                            Color(0xFF732EF0)
                        )
                    ),
                    RoundedCornerShape(13.dp)
                )
                .clickable {
                    if (isSaving) return@clickable

                    val finalGoalName =
                        if (goalName.isBlank()) {
                            "Study ${hours.toInt()}h ${String.format("%02d", minutes.toInt())}m"
                        } else {
                            goalName
                        }

                    val targetMinutes = (hours.toInt() * 60) + minutes.toInt()

                    if (targetMinutes <= 0) {
                        Toast.makeText(context, "Goal time must be greater than 0", Toast.LENGTH_SHORT).show()
                        return@clickable
                    }

                    scope.launch {
                        isSaving = true

                        try {
                            val response = RetrofitClient.api.createGoal(
                                GoalRequest(
                                    user_id = userId,
                                    title = finalGoalName,
                                    goal_type = selectedTab,
                                    target_minutes = targetMinutes
                                )
                            )

                            if (response.isSuccessful) {
                                val createdGoal = response.body()

                                val newGoal = StudyGoal(
                                    name = createdGoal?.title ?: finalGoalName,
                                    type = createdGoal?.goal_type ?: selectedTab,
                                    hours = (createdGoal?.target_minutes ?: targetMinutes) / 60,
                                    minutes = (createdGoal?.target_minutes ?: targetMinutes) % 60,
                                    completedHours = (createdGoal?.current_minutes ?: 0) / 60,
                                    completedMinutes = (createdGoal?.current_minutes ?: 0) % 60,
                                    reminderEnabled = reminderEnabled,
                                    reminderTime = selectedTime
                                )

                                Toast.makeText(context, "Goal saved successfully!", Toast.LENGTH_SHORT).show()
                                onSaveGoalClick(newGoal)
                            } else {
                                Toast.makeText(context, "Failed to save goal", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Backend error: ${e.message}", Toast.LENGTH_LONG).show()
                        } finally {
                            isSaving = false
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isSaving) "Saving..." else if (goalToEdit == null) "✓ Save Goal" else "✓ Save Changes",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GoalTab(
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(22.dp))
            .background(if (selected) Color(0xFF2B145F) else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color(0xFFB56DFF) else Color(0xFFB6B1C9),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TimeBox(value: String, label: String) {
    Box(
        modifier = Modifier
            .width(105.dp)
            .height(95.dp)
            .background(Color(0xFF101034), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF2C1768), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = label,
                color = Color(0xFFB5ADC8),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun SectionTitle(title: String, subtitle: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = subtitle,
        color = Color(0xFF9CA3AF),
        fontSize = 12.sp
    )
}

@Composable
fun InputGoalBox(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF0D1328), RoundedCornerShape(12.dp))
            .border(0.8.dp, Color(0xFF1F2945), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "◎",
                color = Color(0xFF9B5CFF),
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 13.sp
                ),
                cursorBrush = Brush.verticalGradient(
                    listOf(Color(0xFFB56DFF), Color(0xFF7B38FF))
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                color = Color(0xFF9CA3AF),
                                fontSize = 13.sp
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}