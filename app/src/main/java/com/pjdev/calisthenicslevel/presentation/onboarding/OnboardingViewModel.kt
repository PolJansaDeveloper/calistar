package com.pjdev.calisthenicslevel.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.floor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun updatePushReps(value: Int) = updateMetrics(pushReps = value.coerceAtLeast(0))

    fun updatePullReps(value: Int) = updateMetrics(pullReps = value.coerceAtLeast(0))

    fun updateCoreSeconds(value: Int) = updateMetrics(coreSeconds = value.coerceAtLeast(0))

    fun updateLegsReps(value: Int) = updateMetrics(legsReps = value.coerceAtLeast(0))

    fun startAnalysis() {
        viewModelScope.launch {
            _uiState.update { it.copy(isAnalyzing = true) }
            delay(1400)
            _uiState.update { it.copy(isAnalyzing = false) }
        }
    }

    private fun updateMetrics(
        pushReps: Int = _uiState.value.pushReps,
        pullReps: Int = _uiState.value.pullReps,
        coreSeconds: Int = _uiState.value.coreSeconds,
        legsReps: Int = _uiState.value.legsReps
    ) {
        val scoreBase = pushReps * 1.0 + pullReps * 1.6 + (coreSeconds / 5.0) + (legsReps / 2.0)
        val scaled = scoreBase / 4.0
        val level = (floor(scaled) + 1).toInt().coerceIn(1, 50)
        val tier = when (level) {
            in 1..10 -> "Beginner"
            in 11..25 -> "Intermediate"
            in 26..35 -> "Advanced"
            in 36..45 -> "Master"
            else -> "Legend"
        }
        val progress = (scaled - floor(scaled)).toFloat().coerceIn(0f, 1f)

        _uiState.update {
            it.copy(
                pushReps = pushReps,
                pullReps = pullReps,
                coreSeconds = coreSeconds,
                legsReps = legsReps,
                scoreBase = scoreBase,
                level = level,
                tier = tier,
                progress = progress
            )
        }
    }
}
