package com.thmanyah.data.di

import com.thmanyah.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ApiServicesModule {
    @Singleton
    @Provides
    fun provideApiService(
        retrofit : Retrofit,
    ) : ApiService = retrofit.create(ApiService::class.java)

}