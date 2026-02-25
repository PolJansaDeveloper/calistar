package com.pjdev.calisthenicslevel.presentation.onboarding

data class OnboardingState(
    val pushReps: Int = 0,
    val pullReps: Int = 0,
    val coreSeconds: Int = 0,
    val legsReps: Int = 0,
    val scoreBase: Double = 0.0,
    val level: Int = 1,
    val tier: String = "Beginner",
    val progress: Float = 0f,
    val isAnalyzing: Boolean = false
)
