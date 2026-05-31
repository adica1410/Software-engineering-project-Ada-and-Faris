package com.example.focusmodeapp.ui

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmodeapp.R
import com.example.focusmodeapp.RegisterRequest
import com.example.focusmodeapp.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen(
    onBackClick: () -> Unit,
    onAccountCreated: (String, String, String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A0B35),
                        Color(0xFF071124),
                        Color(0xFF030714)
                    ),
                    radius = 1150f
                )
            )
    ) {

        HeaderBackground()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp, bottom = 34.dp)
        ) {

            Text(
                text = "‹",
                color = Color.White,
                fontSize = 34.sp,
                modifier = Modifier.clickable { onBackClick() }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 12.dp)
                ) {

                    Text(
                        text = "Create Account",
                        color = Color.White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Join Focus Mode and start your\njourney to better focus.",
                        color = Color(0xFFB6ACC8),
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(112.dp)
                )
            }

            Spacer(modifier = Modifier.height(42.dp))

            InputBox(
                icon = "♙",
                title = "Full Name",
                hint = "Enter your full name",
                value = fullName,
                onValueChange = { fullName = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            InputBox(
                icon = "✉",
                title = "Email Address",
                hint = "Enter your email",
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            InputBox(
                icon = "▣",
                title = "Password",
                hint = "Enter your password",
                value = password,
                onValueChange = { password = it },
                isPassword = true,
                passwordVisible = showPassword,
                onEyeClick = { showPassword = !showPassword }
            )

            Spacer(modifier = Modifier.height(14.dp))

            InputBox(
                icon = "▣",
                title = "Confirm Password",
                hint = "Confirm your password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isPassword = true,
                passwordVisible = showConfirmPassword,
                onEyeClick = { showConfirmPassword = !showConfirmPassword }
            )

            Spacer(modifier = Modifier.height(20.dp))

            SafeBox()

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (
                        fullName.isNotBlank() &&
                        email.isNotBlank() &&
                        password.isNotBlank() &&
                        confirmPassword == password
                    ) {
                        scope.launch {
                            isLoading = true

                            try {
                                val response = RetrofitClient.api.register(
                                    RegisterRequest(
                                        full_name = fullName,
                                        email = email,
                                        password = password
                                    )
                                )

                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Account created successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    onAccountCreated(fullName, email, password)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Registration failed. Email may already exist.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Backend connection error: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill all fields correctly",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                        text = if (isLoading) "Creating..." else "Create Account",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "→",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(Color(0xFF28304A))
                )

                Text(
                    text = "OR",
                    color = Color(0xFFB3A9C8),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(Color(0xFF28304A))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Already have an account? ",
                    color = Color(0xFFB3A9C8),
                    fontSize = 14.sp
                )

                Text(
                    text = "Log In",
                    color = Color(0xFF9B5CFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HeaderBackground() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {

        val mountain = Path().apply {
            moveTo(0f, size.height * 0.70f)
            lineTo(size.width * 0.47f, size.height * 0.30f)
            lineTo(size.width, size.height * 0.66f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = mountain,
            color = Color(0xFF2A1755).copy(alpha = 0.78f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                listOf(
                    Color(0xFFFF665C).copy(alpha = 0.55f),
                    Color.Transparent
                )
            ),
            radius = 58f,
            center = Offset(size.width * 0.68f, size.height * 0.60f)
        )
    }
}

@Composable
fun InputBox(
    icon: String,
    title: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onEyeClick: (() -> Unit)? = null
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(
                color = Color(0xFF11162D).copy(alpha = 0.97f),
                shape = RoundedCornerShape(13.dp)
            )
            .border(
                width = 0.8.dp,
                color = Color(0xFF252747),
                shape = RoundedCornerShape(13.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                color = Color(0xFF9B5CFF),
                fontSize = 24.sp,
                modifier = Modifier.width(38.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(7.dp))

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    cursorBrush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFB56DFF),
                            Color(0xFF7B38FF)
                        )
                    ),
                    visualTransformation = if (isPassword && !passwordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    decorationBox = { innerTextField ->

                        Column {

                            Box {
                                if (value.isEmpty()) {
                                    Text(
                                        text = hint,
                                        color = Color(0xFFB3A9C8),
                                        fontSize = 14.sp
                                    )
                                }

                                innerTextField()
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(
                                        if (value.isNotEmpty()) {
                                            Color(0xFF7B38FF)
                                        } else {
                                            Color.Transparent
                                        }
                                    )
                            )
                        }
                    }
                )
            }

            if (isPassword) {
                Text(
                    text = if (passwordVisible) "◉" else "⊘",
                    color = Color(0xFF8A829B),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            onEyeClick?.invoke()
                        }
                )
            }
        }
    }
}

@Composable
fun SafeBox() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(
                color = Color(0xFF18133A).copy(alpha = 0.95f),
                shape = RoundedCornerShape(13.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Canvas(modifier = Modifier.size(36.dp)) {

                val purple = Color(0xFF9B5CFF)

                val shield = Path().apply {
                    moveTo(center.x, 2f)
                    lineTo(size.width - 5f, 9f)
                    lineTo(size.width - 8f, 23f)
                    lineTo(center.x, size.height - 2f)
                    lineTo(5f, 23f)
                    lineTo(3f, 9f)
                    close()
                }

                drawPath(
                    path = shield,
                    color = purple,
                    style = Stroke(
                        width = 4f,
                        cap = StrokeCap.Round
                    )
                )

                drawLine(
                    purple,
                    Offset(center.x - 6f, center.y),
                    Offset(center.x - 1f, center.y + 6f),
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )

                drawLine(
                    purple,
                    Offset(center.x - 1f, center.y + 6f),
                    Offset(center.x + 8f, center.y - 7f),
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    text = "Your data is safe with us",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "We use industry-standard encryption\nto protect your information.",
                    color = Color(0xFFB3A9C8),
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}