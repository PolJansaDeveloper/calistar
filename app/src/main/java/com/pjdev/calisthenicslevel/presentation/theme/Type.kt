package com.pjdev.calisthenicslevel.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 44.sp,
        lineHeight = 50.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = (-0.6).sp
    ),
    displayMedium = TextStyle(
        fontSize = 36.sp,
        lineHeight = 42.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = (-0.3).sp
    ),
    headlineLarge = TextStyle(
        fontSize = 30.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontSize = 20.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontSize = 17.sp,
        lineHeight = 23.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.2.sp
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.35.sp
    )
)
