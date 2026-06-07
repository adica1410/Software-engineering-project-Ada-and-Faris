package com.example.focusmodeapp

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("focus_mode_user", Context.MODE_PRIVATE)

    var notifications by remember {
        mutableStateOf(prefs.getBoolean("notifications_enabled", true))
    }

    var sound by remember {
        mutableStateOf(prefs.getBoolean("sound_enabled", true))
    }

    var darkMode by remember {
        mutableStateOf(prefs.getBoolean("dark_mode_enabled", true))
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
                .padding(horizontal = 24.dp)
                .padding(top = 44.dp, bottom = 40.dp)
        ) {
            Text(
                text = "‹",
                color = Color.White,
                fontSize = 38.sp,
                modifier = Modifier.clickable {
                    onBackClick()
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Settings",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Customize your focus experience.",
                color = Color(0xFFB6B1C9),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            SettingsSwitchRow(
                icon = "☾",
                title = "Dark Mode",
                subtitle = "Keep the app in dark theme",
                checked = darkMode,
                onCheckedChange = {
                    darkMode = it
                    prefs.edit().putBoolean("dark_mode_enabled", it).apply()
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsSwitchRow(
                icon = "🔔",
                title = "Notifications",
                subtitle = "Enable study reminders",
                checked = notifications,
                onCheckedChange = {
                    notifications = it
                    prefs.edit().putBoolean("notifications_enabled", it).apply()
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsSwitchRow(
                icon = "🔊",
                title = "Sound",
                subtitle = "Focus session sound effects",
                checked = sound,
                onCheckedChange = {
                    sound = it
                    prefs.edit().putBoolean("sound_enabled", it).apply()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SettingsActionRow(
                icon = "🔒",
                title = "Change Password",
                subtitle = "Update your account password",
                onClick = onChangePasswordClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            SettingsActionRow(
                icon = "🚪",
                title = "Logout",
                subtitle = "Sign out from this account",
                onClick = onLogoutClick,
                danger = true
            )
        }
    }
}

@Composable
fun SettingsSwitchRow(
    icon: String,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(18.dp))
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 28.sp)

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color(0xFFAAA6BB), fontSize = 12.sp)
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingsActionRow(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    danger: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color(0xFF11162D), RoundedCornerShape(18.dp))
            .border(0.8.dp, Color(0xFF252B4C), RoundedCornerShape(18.dp))
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 28.sp)

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = if (danger) Color(0xFFFF4D6D) else Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(subtitle, color = Color(0xFFAAA6BB), fontSize = 12.sp)
        }

        Text(
            text = "›",
            color = Color(0xFF9B5CFF),
            fontSize = 26.sp
        )
    }
}

