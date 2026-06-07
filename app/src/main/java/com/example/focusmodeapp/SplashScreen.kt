package com.example.focusmodeapp.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmodeapp.R

@Composable
fun SplashScreen() {

    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF24104D),
                        Color(0xFF120822),
                        Color(0xFF05020D)
                    ),
                    radius = 1200f
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        SplashBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.offset(y = (-10).dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Focus Mode Logo",
                modifier = Modifier.size(175.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Focus ",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Mode",
                    color = Color(0xFF8B5CFF),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Stay focused. Achieve more.",
                color = Color(0xFFB4A7CC),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(75.dp))

            Canvas(
                modifier = Modifier.size(52.dp)
            ) {
                rotate(rotation) {
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF6F42FF),
                                Color(0xFF9B5CFF)
                            )
                        ),
                        startAngle = 0f,
                        sweepAngle = 285f,
                        useCenter = false,
                        style = Stroke(
                            width = 6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "L O A D I N G . . .",
                color = Color(0xFFB4A7CC),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 4.sp
            )
        }
    }
}

@Composable
fun SplashBackground() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        val centerPoint = Offset(
            x = size.width / 2f,
            y = size.height * 0.34f
        )

        repeat(4) { index ->
            drawCircle(
                color = Color(0xFF7B4DFF).copy(alpha = 0.055f),
                radius = 150f + index * 110f,
                center = centerPoint,
                style = Stroke(width = 1.5f)
            )
        }

        val stars = listOf(
            Offset(size.width * 0.08f, size.height * 0.07f),
            Offset(size.width * 0.14f, size.height * 0.30f),
            Offset(size.width * 0.09f, size.height * 0.43f),
            Offset(size.width * 0.52f, size.height * 0.27f),
            Offset(size.width * 0.82f, size.height * 0.09f),
            Offset(size.width * 0.88f, size.height * 0.28f),
            Offset(size.width * 0.84f, size.height * 0.47f),
            Offset(size.width * 0.86f, size.height * 0.72f),
            Offset(size.width * 0.15f, size.height * 0.78f)
        )

        stars.forEach {
            drawCircle(
                color = Color(0xFF8B5CFF).copy(alpha = 0.85f),
                radius = 2.3f,
                center = it
            )
        }
    }
}