package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.BooruScreen
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.SampleImageRepository
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import com.makentoshe.booruchan.postsamples.model.SamplePageController
import com.makentoshe.booruchan.postsamples.model.SamplePagePagerAdapter

class PostsSampleFragmentViewModel(
    private val booru: Booru,
    val startPosition: Int,
    private val postsRepository: PostsRepository,
    private val sampleRepository: ImageRepository
) : ViewModel() {

    val blockController = SamplePageController()

    /**
     * @param position post index start from 0.
     */
    fun getPost(position: Int): Post {
        val posts = postsRepository.get(position / postsRepository.count)
        return posts[position % postsRepository.count]
    }

    fun startNewSearch(tags: Set<Tag> = setOf(Tag("hatsune_miku"))) {
        Booruchan.INSTANCE.router.newChain(BooruScreen(booru, tags.toHashSet()))
    }

    fun getPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplePagePagerAdapter(fragmentManager, blockController)
    }

    fun update() {
        blockController.update()
    }

    fun backToPreviews() = Booruchan.INSTANCE.router.exit()

}