package com.example.focusmodeapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmodeapp.R

@Composable
fun WelcomeScreen(
    onCreateAccountClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A0B35),
                        Color(0xFF070B1E),
                        Color(0xFF030714)
                    ),
                    radius = 1100f
                )
            )
    ) {
        Text(
            text = "Skip  ›",
            color = Color(0xFF9B5CFF),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 54.dp, end = 28.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 95.dp, bottom = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(98.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Welcome to",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Focus Mode",
                color = Color(0xFF9B5CFF),
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your personal productivity partner.\nStay focused. Achieve more.",
                color = Color(0xFFB3A9C8),
                fontSize = 16.sp,
                lineHeight = 23.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(34.dp))

            FeatureCard(
                type = 1,
                title = "Track your focus",
                description = "Monitor your study sessions and\nsee your progress in real time."
            )

            Spacer(modifier = Modifier.height(14.dp))

            FeatureCard(
                type = 2,
                title = "Beat distractions",
                description = "Block distracting websites and\napps to protect your focus."
            )

            Spacer(modifier = Modifier.height(14.dp))

            FeatureCard(
                type = 3,
                title = "Achieve your goals",
                description = "Set goals, build streaks, earn\nbadges and stay motivated."
            )

            Spacer(modifier = Modifier.height(40.dp))

            GradientButton(text = "Log In")

            Spacer(modifier = Modifier.height(16.dp))

            OutlineButton(
                text = "Create Account",
                onClick = onCreateAccountClick
            )
        }
    }
}

@Composable
fun GradientButton(text: String) {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp),
        shape = RoundedCornerShape(11.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF8438FF),
                            Color(0xFF732EF0)
                        )
                    ),
                    RoundedCornerShape(11.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "→",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 30.dp)
            )
        }
    }
}

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clickable { onClick() }
            .border(
                width = 1.5.dp,
                color = Color(0xFF8B3DFF),
                shape = RoundedCornerShape(11.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "→",
            color = Color(0xFF9B5CFF),
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 30.dp)
        )
    }
}

@Composable
fun FeatureCard(
    type: Int,
    title: String,
    description: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(
                color = Color(0xFF11162D).copy(alpha = 0.96f),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 0.7.dp,
                color = Color(0xFF2B2750),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            when (type) {
                1 -> drawRightChart()
                2 -> drawRightLock()
                3 -> drawRightFlag()
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                Color(0xFF6530D9),
                                Color(0xFF231245)
                            )
                        ),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                MiniIcon(type)
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    color = Color(0xFFB5ADC8),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

fun DrawScope.drawRightChart() {
    val purple = Color(0xFF9B5CFF)

    drawCircle(
        color = purple.copy(alpha = 0.20f),
        radius = 32f,
        center = Offset(size.width * 0.91f, size.height * 0.58f)
    )

    val path = Path().apply {
        moveTo(size.width * 0.79f, size.height * 0.58f)
        cubicTo(
            size.width * 0.83f,
            size.height * 0.75f,
            size.width * 0.87f,
            size.height * 0.30f,
            size.width * 0.95f,
            size.height * 0.36f
        )
    }

    drawPath(
        path = path,
        color = purple,
        style = Stroke(width = 3.5f, cap = StrokeCap.Round)
    )

    drawCircle(
        color = purple,
        radius = 5.5f,
        center = Offset(size.width * 0.95f, size.height * 0.36f)
    )
}

fun DrawScope.drawRightLock() {
    val purple = Color(0xFF9B5CFF)

    drawRoundRect(
        color = purple.copy(alpha = 0.22f),
        topLeft = Offset(size.width * 0.86f, size.height * 0.54f),
        size = Size(34f, 27f),
        cornerRadius = CornerRadius(5f, 5f)
    )

    drawRoundRect(
        color = purple.copy(alpha = 0.35f),
        topLeft = Offset(size.width * 0.84f, size.height * 0.18f),
        size = Size(42f, 40f),
        cornerRadius = CornerRadius(8f, 8f),
        style = Stroke(width = 3f)
    )

    drawArc(
        color = purple.copy(alpha = 0.70f),
        startAngle = 205f,
        sweepAngle = 130f,
        useCenter = false,
        topLeft = Offset(size.width * 0.88f, size.height * 0.38f),
        size = Size(32f, 32f),
        style = Stroke(width = 4f, cap = StrokeCap.Round)
    )
}

fun DrawScope.drawRightFlag() {
    val purple = Color(0xFF7B3CFF)

    val mountain = Path().apply {
        moveTo(size.width * 0.77f, size.height * 0.82f)
        lineTo(size.width * 0.88f, size.height * 0.29f)
        lineTo(size.width * 0.98f, size.height * 0.82f)
        close()
    }

    drawPath(
        path = mountain,
        color = purple.copy(alpha = 0.42f)
    )

    drawRect(
        color = Color(0xFF9B5CFF),
        topLeft = Offset(size.width * 0.885f, size.height * 0.24f),
        size = Size(4f, 30f)
    )

    val flag = Path().apply {
        moveTo(size.width * 0.895f, size.height * 0.24f)
        lineTo(size.width * 0.95f, size.height * 0.31f)
        lineTo(size.width * 0.895f, size.height * 0.38f)
        close()
    }

    drawPath(flag, Color(0xFF9B5CFF))
}

@Composable
fun MiniIcon(type: Int) {
    Canvas(modifier = Modifier.size(34.dp)) {
        val purple = Color(0xFFB279FF)

        when (type) {
            1 -> {
                drawCircle(purple, 10f, center, style = Stroke(width = 4f))
                drawCircle(purple, 4f, center)
                drawLine(purple, Offset(center.x, 0f), Offset(center.x, 8f), strokeWidth = 3f)
                drawLine(purple, Offset(center.x, size.height), Offset(center.x, size.height - 8f), strokeWidth = 3f)
                drawLine(purple, Offset(0f, center.y), Offset(8f, center.y), strokeWidth = 3f)
                drawLine(purple, Offset(size.width, center.y), Offset(size.width - 8f, center.y), strokeWidth = 3f)
            }

            2 -> {
                val shield = Path().apply {
                    moveTo(center.x, 2f)
                    lineTo(size.width - 5f, 8f)
                    lineTo(size.width - 8f, 22f)
                    lineTo(center.x, size.height - 2f)
                    lineTo(5f, 22f)
                    lineTo(3f, 8f)
                    close()
                }

                drawPath(
                    shield,
                    purple,
                    style = Stroke(width = 4f, cap = StrokeCap.Round)
                )

                drawLine(
                    purple,
                    Offset(center.x - 5f, center.y),
                    Offset(center.x - 1f, center.y + 5f),
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )

                drawLine(
                    purple,
                    Offset(center.x - 1f, center.y + 5f),
                    Offset(center.x + 7f, center.y - 6f),
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
            }

            3 -> {
                drawRoundRect(
                    color = purple,
                    topLeft = Offset(8f, 8f),
                    size = Size(16f, 14f),
                    cornerRadius = CornerRadius(4f, 4f),
                    style = Stroke(width = 4f)
                )

                drawLine(
                    purple,
                    Offset(center.x, 22f),
                    Offset(center.x, 28f),
                    strokeWidth = 4f
                )

                drawLine(
                    purple,
                    Offset(9f, 29f),
                    Offset(25f, 29f),
                    strokeWidth = 4f
                )

                drawArc(
                    purple,
                    90f,
                    120f,
                    false,
                    topLeft = Offset(1f, 8f),
                    size = Size(13f, 13f),
                    style = Stroke(width = 3f)
                )

                drawArc(
                    purple,
                    -30f,
                    120f,
                    false,
                    topLeft = Offset(20f, 8f),
                    size = Size(13f, 13f),
                    style = Stroke(width = 3f)
                )
            }
        }
    }
}