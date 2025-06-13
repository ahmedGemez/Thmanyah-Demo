package com.thmanyah.data.di

import com.thmanyah.data.ApiService
import com.thmanyah.data.repositoriesImpl.HomeRepoImpl
import com.thmanyah.domain.repositories.HomeRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Singleton
    @Provides
    fun provideRemoteRepo(apiService: ApiService): HomeRepo {
        return HomeRepoImpl(apiService)
    }
}