package com.makentoshe.booruchan.screen.posts

import android.view.View
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import com.makentoshe.booruchan.screen.search.model.TagsController
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class PostsUiToolbarSearchInflator(
    private val fragmentManager: FragmentManager,
    private val tagsController: TagsController
) : Inflator {
    override fun inflate(view: View) {
        view.find<View>(R.id.posts_toolbar_search).onClick {
            println(tagsController.tags)
            SearchDialogFragment.create(tagsController)
                .show(fragmentManager, SearchDialogFragment::class.java.simpleName)
        }
    }
}
