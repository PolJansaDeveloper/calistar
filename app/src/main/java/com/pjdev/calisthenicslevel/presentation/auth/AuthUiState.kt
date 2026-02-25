package com.pjdev.calisthenicslevel.presentation.auth

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoggedIn: Boolean = false,
    val isRegisterMode: Boolean = false
)
