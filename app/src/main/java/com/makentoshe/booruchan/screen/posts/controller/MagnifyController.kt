package com.makentoshe.booruchan.screen.posts.controller

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.screen.posts.viewmodel.TagsViewModel
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import org.jetbrains.anko.find

class MagnifyController(
    private val booru: Booru,
    private val targetFragment: Fragment,
    private val tagsViewModel: TagsViewModel
) {

    fun bindView(view: View) {
        val search = view.find<View>(R.id.posts_toolbar_search)

        search.setOnClickListener(::onClicked)
    }

    private fun onClicked(view: View) {
        val fragment =
            SearchDialogFragment.create(tagsViewModel.set, booru)
        fragment.setTargetFragment(targetFragment, RequestCode.search)
        fragment.show(targetFragment.fragmentManager, SearchDialogFragment::class.java.simpleName)
    }
}