package com.example.focusmodeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedScreen: String,
    onHomeClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onBlockedClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
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
            AppBottomNavItem(
                icon = "⌂",
                title = "Home",
                selected = selectedScreen == "Home",
                onClick = onHomeClick
            )

            AppBottomNavItem(
                icon = "▥",
                title = "Statistics",
                selected = selectedScreen == "Statistics",
                onClick = onStatisticsClick
            )

            AppBottomNavItem(
                icon = "◎",
                title = "Goals",
                selected = selectedScreen == "Goals",
                onClick = onGoalsClick
            )

            AppBottomNavItem(
                icon = "⬡",
                title = "Blocked",
                selected = selectedScreen == "Blocked",
                onClick = onBlockedClick
            )

            AppBottomNavItem(
                icon = "♙",
                title = "Profile",
                selected = selectedScreen == "Profile",
                onClick = onProfileClick
            )
        }
    }
}

@Composable
fun AppBottomNavItem(
    icon: String,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .clickable { onClick() }
            .padding(vertical = 6.dp)
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