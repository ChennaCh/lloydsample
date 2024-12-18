package com.chenna.lloydsamplepoject.di

import com.chenna.data.repository.TvShowRepositoryImpl
import com.chenna.domain.repository.TvShowRepository
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.usecase.impl.ShowsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ShowModule {
    @Binds
    abstract fun bindsShowUseCase(useCaseImpl: ShowsUseCaseImpl): ShowsUseCase

    @Binds
    abstract fun bindsShowRepository(repositoryImpl: TvShowRepositoryImpl): TvShowRepository
}