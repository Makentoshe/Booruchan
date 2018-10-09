package com.makentoshe.booruchan.booru.content.comments.vertical.pager.view

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.makentoshe.booruchan.booru.content.comments.vertical.pager.PageViewModel
import com.makentoshe.booruchan.booru.content.comments.vertical.pager.model.CommentsRecycleViewAdapter
import com.makentoshe.booruchan.common.runOnUi
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class PageFragmentUI(private val page: Int,
                     private val viewModel: PageViewModel)
    : AnkoComponent<PageFragment> {

    override fun createView(ui: AnkoContext<PageFragment>): View = with(ui) {
        relativeLayout {
            recyclerView {
                viewModel.getCommentedPosts(page * 10, ui.owner) {
                    runOnUi {
                        val lm = LinearLayoutManager(context)
                        lm.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = lm
                        adapter = CommentsRecycleViewAdapter(it!!, viewModel.previewLoader)
                    }
                }
            }
        }
    }
}