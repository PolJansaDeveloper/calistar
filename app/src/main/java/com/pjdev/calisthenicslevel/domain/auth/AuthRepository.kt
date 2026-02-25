package com.pjdev.calisthenicslevel.domain.auth

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun loginWithGoogle(): Result<Unit>
    fun isLoggedIn(): Flow<Boolean>
    suspend fun logout(): Result<Unit>
}
