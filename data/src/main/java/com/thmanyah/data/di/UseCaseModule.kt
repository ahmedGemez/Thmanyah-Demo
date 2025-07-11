package com.thmanyah.data.di

import com.thmanyah.domain.repositories.HomeRepo
import com.thmanyah.domain.usecases.GetHomeDataUseCase
import com.thmanyah.domain.usecases.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetHomeDataUseCase(
        homeRepository: HomeRepo
    ): GetHomeDataUseCase = GetHomeDataUseCase(homeRepository)

    @Provides
    fun provideSearchUseCase(
        homeRepository: HomeRepo
    ): SearchUseCase = SearchUseCase(homeRepository)
}