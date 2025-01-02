package com.mortex.mortext.domain.di

import android.content.Context
import androidx.room.Room
import com.mortex.mortext.data.repository.RepositoryImpl
import com.mortex.mortext.domain.db.UserDataBase
import com.mortex.mortext.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUsersUseCase(impl: RepositoryImpl): GetUsersUseCase {
        return GetUsersUseCase(impl)
    }




}