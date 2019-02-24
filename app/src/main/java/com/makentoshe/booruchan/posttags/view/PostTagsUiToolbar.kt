package com.makentoshe.booruchan.posttags.view

import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.view.setToolbarIcon
import com.makentoshe.booruchan.postpreviews.viewmodel.TagsViewModel
import com.makentoshe.booruchan.posttags.model.PostTagsNavigator
import org.jetbrains.anko.*

class PostTagsUiToolbar(
    private val searchController: TagsViewModel,
    private val navigator: PostTagsNavigator
) : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.posttags_toolbar
            backgroundColorResource = style.toolbar.primaryColorRes
            createSearch()
            createBack()
        }.lparams(matchParent, dip(56)) {
            alignParentTop()
        }
    }

    private fun _RelativeLayout.createSearch() = frameLayout {
        id = R.id.posttags_toolbar_search
        setOnClickListener(::onSearchIconClicked)
        visibility = View.GONE
        imageView {
            setToolbarIcon(style, style.drawable.static.magnify)
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }

        searchController.subscribeOnChange { tag, b ->
            if (searchController.currentlySelectedTags.isNotEmpty()) {
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }

    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun onSearchIconClicked(ignored: View) {
        navigator.startNewSearch(searchController.currentlySelectedTags)
    }

    private fun _RelativeLayout.createBack() = frameLayout {
        id = R.id.posttags_toolbar_back
        setOnClickListener(::onBackIconClicked)
        imageView {
            setToolbarIcon(style, style.drawable.static.arrow)
            rotation = -90f
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }
    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun onBackIconClicked(ignored: View) {
        navigator.back()
    }
}