package com.makentoshe.booruchan.postsamples

import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.BooruScreen
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.posts.model.PostsRepository

class PostsSampleFragmentViewModel(
    private val booru: Booru,
    val startPosition: Int,
    private val postsRepository: PostsRepository
) : ViewModel() {

    /**
     * @param position post index start from 0.
     */
    fun getPost(position: Int): Post {
        val posts = postsRepository.get(position / postsRepository.count)
        return posts[position % postsRepository.count]
    }

    fun startNewSearch() {
        val set = hashSetOf(Tag("hatsune_miku"))
        Booruchan.INSTANCE.router.newChain(BooruScreen(booru, set))
    }

//    fun getPagerAdapter(): PagerAdapter {
//
//    }
}