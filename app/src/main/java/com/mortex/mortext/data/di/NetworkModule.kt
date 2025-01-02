package com.mortex.mortext.data.di

import android.content.Context
import androidx.room.Room
import com.mortex.mortext.data.network.UserService
import com.mortex.mortext.data.repository.RepositoryImpl
import com.mortex.mortext.domain.db.UserDao
import com.mortex.mortext.domain.db.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client:OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }


    @Provides
    @Singleton
    fun provideRepoImpl(userService: UserService,userDao: UserDao): RepositoryImpl {
        return RepositoryImpl(userService,userDao)
    }

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        UserDataBase::class.java,
        "user_db"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: UserDataBase) = db.userDao()

}