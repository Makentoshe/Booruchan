package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import com.makentoshe.booruchan.screen.posts.model.SearchController
import com.makentoshe.booruchan.screen.posts.model.TagsController
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class PostsUiToolbarSearchInflator(
    private val fragmentManager: FragmentManager,
    private val tagsController: TagsController,
    private val searchController: SearchController
) : Inflator {
    override fun inflate(view: View) {
        val searchicon = view.find<View>(R.id.posts_toolbar_search)
        searchicon.onClick { showSearchFragment() }
    }

    private fun showSearchFragment() {
        SearchDialogFragment.create(tagsController, searchController)
            .show(fragmentManager, SearchDialogFragment::class.java.simpleName)
    }
}