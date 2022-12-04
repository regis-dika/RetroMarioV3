package com.example.retromariokmm.android.di

import com.example.retromariokmm.domain.usecases.login.LoginUseCase
import com.example.retromariokmm.domain.usecases.users.UserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providesLoginUseCase() = LoginUseCase()
    @Provides
    fun providesRetroUsers() = UserListUseCase()
}