package com.makentoshe.booruchan.screen.posts.container.controller

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.model.TagsHolder
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import org.jetbrains.anko.find

class PostsMagnifyController(
    private val booru: Booru,
    private val targetFragment: Fragment,
    private val tags: Set<Tag>
) {

    fun bindView(view: View) {
        val search = view.find<View>(R.id.posts_toolbar_search)

        search.setOnClickListener(::onClicked)
    }

    private fun onClicked(view: View) {
        val fragment = SearchDialogFragment.create(tags, booru)
        fragment.setTargetFragment(targetFragment, RequestCode.search)
        fragment.show(targetFragment.requireFragmentManager(), SearchDialogFragment::class.java.simpleName)
    }
}