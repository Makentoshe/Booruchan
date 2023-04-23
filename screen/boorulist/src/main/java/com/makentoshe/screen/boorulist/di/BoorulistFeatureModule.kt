package com.makentoshe.screen.boorulist.di

import com.makentoshe.booruchan.feature.boorulist.data.repository.BooruContextRepositoryImpl
import com.makentoshe.booruchan.feature.boorulist.domain.repository.BooruContextRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BoorulistScreenModule {

    @Binds
    abstract fun bindsBooruContextRepository(impl: BooruContextRepositoryImpl): BooruContextRepository
}