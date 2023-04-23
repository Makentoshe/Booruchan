package com.makentoshe.booruchan.screen.splash.di

import com.makentoshe.booruchan.feature.boorulist.data.mapper.DatastoredBooruContext2BooruContextMapperImpl
import com.makentoshe.booruchan.feature.boorulist.data.mapper.String2DatastoredBooruContextMapperImpl
import com.makentoshe.booruchan.feature.boorulist.data.repository.ApplicationAssetRepositoryImpl
import com.makentoshe.booruchan.feature.boorulist.data.repository.BooruContextRepositoryImpl
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.DatastoredBooruContext2BooruContextMapper
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.String2DatastoredBooruContextMapper
import com.makentoshe.booruchan.feature.boorulist.domain.repository.ApplicationAssetRepository
import com.makentoshe.booruchan.feature.boorulist.domain.repository.BooruContextRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SplashScreenModule {

    @Binds
    abstract fun bindsDatastoredBooruContext2BooruContextMapper(
        impl: DatastoredBooruContext2BooruContextMapperImpl,
    ): DatastoredBooruContext2BooruContextMapper

    @Binds
    abstract fun bindString2DatastoredBooruContextMapper(
        impl: String2DatastoredBooruContextMapperImpl,
    ): String2DatastoredBooruContextMapper

    @Binds
    abstract fun bindApplicationAssetRepository(
        impl: ApplicationAssetRepositoryImpl
    ): ApplicationAssetRepository

}