package com.makentoshe.booruchan.booru.model.content.posts

import android.support.v4.app.Fragment
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.posts.PostsFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class PostsContent(private val appSettings: AppSettings) : Content {

    private lateinit var fragment: PostsFragment

    override fun createView(contentViewModel: ContentViewModel): Fragment {
        fragment = PostsFragment.new(contentViewModel.getBooru(), appSettings)
        return fragment
    }

    override fun onSearchStarted(): (String?) -> (Unit) {
        return {
            fragment.ui.createNewGalleryAdapterAndScrollToStartPosition(it)
        }
    }

}