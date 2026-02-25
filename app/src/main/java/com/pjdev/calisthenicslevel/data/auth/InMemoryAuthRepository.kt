package com.pjdev.calisthenicslevel.data.auth

import com.pjdev.calisthenicslevel.domain.auth.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryAuthRepository @Inject constructor() : AuthRepository {
    private val loggedInState = MutableStateFlow(false)

    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(600)
        loggedInState.value = true
        return Result.success(Unit)
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        delay(600)
        loggedInState.value = true
        return Result.success(Unit)
    }

    override suspend fun loginWithGoogle(): Result<Unit> {
        delay(600)
        loggedInState.value = true
        return Result.success(Unit)
    }

    override fun isLoggedIn(): Flow<Boolean> = loggedInState

    override suspend fun logout(): Result<Unit> {
        delay(600)
        loggedInState.value = false
        return Result.success(Unit)
    }
}
