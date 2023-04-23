package com.makentoshe.screen.boorulist.di

import com.makentoshe.booruchan.feature.boorulist.data.mapper.DatastoredBooruContext2BooruContextMapperImpl
import com.makentoshe.booruchan.feature.boorulist.data.repository.BooruContextRepositoryImpl
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.DatastoredBooruContext2BooruContextMapper
import com.makentoshe.booruchan.feature.boorulist.domain.repository.BooruContextRepository
import com.makentoshe.booruchan.healthcheck.data.HealthcheckRepositoryImpl
import com.makentoshe.booruchan.healthcheck.domain.HealthcheckRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BoorulistScreenModule {

//    @Binds
//    abstract fun bindsDatastoredBooruContext2BooruContextMapper(
//        impl: DatastoredBooruContext2BooruContextMapperImpl,
//    ): DatastoredBooruContext2BooruContextMapper

    @Binds
    abstract fun bindsBooruContextRepository(
        impl: BooruContextRepositoryImpl,
    ): BooruContextRepository

    @Binds
    abstract fun bindsHealthcheckRepository(
        impl: HealthcheckRepositoryImpl,
    ): HealthcheckRepository
}