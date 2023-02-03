package com.example.retromariokmm.android.di

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.usecases.actions.*
import com.example.retromariokmm.domain.usecases.comments.*
import com.example.retromariokmm.domain.usecases.login.LoginUseCase
import com.example.retromariokmm.domain.usecases.retros.AddUserToRetroRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.ConnectUserToRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.CreateRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.GetAllRetrosUseCase
import com.example.retromariokmm.domain.usecases.users.*
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
    fun provideUpdateLifeUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UpdateLifeUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideUpdateDifficultyUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UpdateDifficultyUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideCurrentUserUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        CurrentUserUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideStarCommentsUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        CommentsListUseCase(firebaseRetroMarioRepositoryImpl)

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

    @Provides
    fun provideActionListUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        ActionListUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideCreateActionUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        CreateActionUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideUpdateLActionUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UpdateActionUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideUpdateActionActorListListUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UpdateActionActorListUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideUpdateActionCheckStateUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        UpdateActionCheckStateUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideGetActionByIdUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        GetActionByIdUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideUpdatedUserUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        GetUserUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideCreateRetroUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        CreateRetroUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideAddUserToRetroUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        AddUserToRetroRetroUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideGetAllRetrosUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        GetAllRetrosUseCase(firebaseRetroMarioRepositoryImpl)

    @Provides
    fun provideConnectToRetroUseCase(firebaseRetroMarioRepositoryImpl: FirebaseRetroMarioRepositoryImpl) =
        ConnectUserToRetroUseCase(firebaseRetroMarioRepositoryImpl)
}