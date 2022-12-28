package com.example.retromariokmm.android.di

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.usecases.comments.*
import com.example.retromariokmm.domain.usecases.login.LoginUseCase
import com.example.retromariokmm.domain.usecases.users.CurrentUserUseCase
import com.example.retromariokmm.domain.usecases.users.SetLifeDifficultyUseCase
import com.example.retromariokmm.domain.usecases.users.UserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideFirebaseRepo(): FirebaseRetroMarioRepositoryImpl = FirebaseRetroMarioRepositoryImpl()

    @Provides
    fun providesLoginUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        LoginUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun providesRetroUsers(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UserListUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideSetLifeAndDifficultyUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        SetLifeDifficultyUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideCurrentUserUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        CurrentUserUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideStarCommentsUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        StarCommentsListUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideCreateStarCommentUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        CreateStarCommentUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideUpdateStarCommentUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UpdateStarCommentUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideGetCommentUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        GetCommentByIdUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideUpdateLikeCommentUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UpdateLikeCommentUseCase(firebaseRetroMarioRepositoryImpl)
}