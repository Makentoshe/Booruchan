package com.makentoshe.booruchan.postpreviews.model

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.repository.*
import com.makentoshe.repository.cache.Cache
import com.makentoshe.repository.cache.PostsCache

class AdapterBuilderImpl(private val booru: Booru) : AdapterBuilder {

    /* Repository which performs autocompletion a CharSequence with the possible variations contains in the list */
    private val autocompleteRepository = DelayAutocompleteRepository(booru)

    override fun getAutocompleteAdapter() =
        DelayAutocompleteAdapter(autocompleteRepository)

    override fun getViewPagerAdapter(fragmentManager: FragmentManager, tags: Set<Tag>): PagerAdapter {
        val postsRepository = buildPostsRepository()
        val previewsRepository = buildPreviewsRepository()
        val samplesRepository = buildSamplesRepository()

        val arguments = PostPageFragment.Arguments(booru, tags, postsRepository, previewsRepository, samplesRepository)

        return ViewPagerAdapter(fragmentManager, arguments)
    }

    private fun buildPostsRepository(cacheSize: Int = 36): ClearableRepository<Booru.PostRequest, Posts> {
        return PostsCachedRepository(PostsCache(cacheSize), PostsRepository(booru))
//        return CachedRepository<Booru.PostRequest, Posts>(Cache.create(cacheSize), PostsRepository(booru))
    }

    private fun buildPreviewsRepository(cacheSize: Int = 36): ClearableRepository<String, ByteArray> {
        return CachedRepository<String, ByteArray>(Cache.create(cacheSize), PreviewImageRepository(booru))
    }

    private fun buildSamplesRepository(cacheSize: Int = 3): SampleImageRepository {
        return SampleImageRepository(booru, Cache.create(cacheSize))
    }
}