package com.pjdev.calisthenicslevel.di

import com.pjdev.calisthenicslevel.data.auth.InMemoryAuthRepository
import com.pjdev.calisthenicslevel.domain.auth.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(repository: InMemoryAuthRepository): AuthRepository
}
