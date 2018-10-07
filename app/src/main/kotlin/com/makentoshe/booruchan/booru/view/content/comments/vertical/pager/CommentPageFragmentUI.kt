package com.makentoshe.booruchan.booru.view.content.comments.vertical.pager

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.makentoshe.booruchan.booru.model.content.comments.pager.vertical.CommentContentPageFragmentRecycleViewAdapter
import com.makentoshe.booruchan.common.runOnUi
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class CommentPageFragmentUI(private val page: Int,
                            private val viewModel: CommentPageFragmentViewModel)
    : AnkoComponent<CommentPageFragment> {

    override fun createView(ui: AnkoContext<CommentPageFragment>): View = with(ui) {
        relativeLayout {
            recyclerView {
                viewModel.getCommentedPosts(page * 10, ui.owner) {
                    runOnUi {
                        val lm = LinearLayoutManager(context)
                        lm.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = lm
                        adapter = CommentContentPageFragmentRecycleViewAdapter(it!!, viewModel.previewLoader)
                    }
                }
            }
        }
    }
}