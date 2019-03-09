package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import com.makentoshe.booruchan.screen.posts.model.SearchController
import com.makentoshe.booruchan.screen.posts.model.TagsController
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class PostsUiToolbarSearchInflator(
    private val fragment: Fragment,
    private val tagsController: TagsController
) : Inflator {
    override fun inflate(view: View) {
        val searchicon = view.find<View>(R.id.posts_toolbar_search)
        searchicon.onClick { showSearchFragment() }
    }

    private fun showSearchFragment() {
        val fragment = SearchDialogFragment.create(tagsController)
        fragment.setTargetFragment(this.fragment, SearchDialogFragment.SEARCH_CODE)
        fragment.show(this.fragment.fragmentManager, SearchDialogFragment::class.java.simpleName)
    }
}