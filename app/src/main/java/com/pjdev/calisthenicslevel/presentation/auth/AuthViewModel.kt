package com.pjdev.calisthenicslevel.presentation.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pjdev.calisthenicslevel.domain.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.isLoggedIn().collect { loggedIn ->
                _uiState.update { it.copy(isLoggedIn = loggedIn) }
            }
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, emailError = null, errorMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, passwordError = null, errorMessage = null) }
    }

    fun onModeChange(register: Boolean) {
        _uiState.update { it.copy(isRegisterMode = register, errorMessage = null) }
    }

    fun consumeError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun submitWithEmail() {
        val state = _uiState.value
        val emailError = if (Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) null else "Email inválido"
        val passwordError = if (state.password.length >= 6) null else "Mínimo 6 caracteres"

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError,
                    errorMessage = "Revisa los campos e inténtalo de nuevo"
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, errorMessage = null) }
            val result = if (state.isRegisterMode) {
                authRepository.register(state.email, state.password)
            } else {
                authRepository.login(state.email, state.password)
            }
            _uiState.update {
                it.copy(
                    loading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun loginWithGoogle() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, errorMessage = null) }
            val result = authRepository.loginWithGoogle()
            _uiState.update {
                it.copy(
                    loading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, errorMessage = null) }
            val result = authRepository.logout()
            _uiState.update {
                it.copy(
                    loading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
            if (result.isSuccess) {
                onComplete()
            }
        }
    }
}
