package com.makentoshe.booruchan.postpreview

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.repository.Repository
import com.makentoshe.repository.SampleImageRepository

class PostPageScreen(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: Repository<Booru.PostRequest, Posts>,
    private val previewsRepository: Repository<String, ByteArray>,
    private val samplesRepository: SampleImageRepository,
    private val tags: Set<Tag>
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostPageFragment.create(booru, position, postsRepository, previewsRepository, samplesRepository, tags)
}