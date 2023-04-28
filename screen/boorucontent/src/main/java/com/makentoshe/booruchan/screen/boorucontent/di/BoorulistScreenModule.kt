package com.makentoshe.booruchan.screen.boorucontent.di

import com.makentoshe.booruchan.feature.boorupost.data.Gelbooru02BoorupostsRepository
import com.makentoshe.booruchan.feature.boorupost.domain.BoorupostsRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BoorucontentScreenModule {

    companion object {

        @ViewModelScoped
        @Provides
        fun providesBooruPostsRepositories(
            gelbooru02BoorupostsRepository: Gelbooru02BoorupostsRepository,
        ) = BoorupostsRepositories(
            listOf(
                gelbooru02BoorupostsRepository,
            )
        )
    }

}