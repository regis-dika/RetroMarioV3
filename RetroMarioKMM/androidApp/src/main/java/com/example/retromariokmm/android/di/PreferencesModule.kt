package com.example.retromariokmm.android.di

import android.content.Context
import com.example.retromariokmm.utils.preferences.KMMContext
import com.example.retromariokmm.utils.preferences.KMMPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun providePreferences(
        context: KMMContext
    ) = KMMPreference(context)
}