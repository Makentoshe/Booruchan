package com.makentoshe.booruchan.booru.view.content.comments

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.content.comments.CommentsContentViewModel
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.onScroll
import com.makentoshe.booruchan.common.onSwipe
import com.makentoshe.booruchan.common.runOnUi
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7._RecyclerView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CommentsFragmentUI(private val viewModel: CommentsContentViewModel)
    : AnkoComponent<CommentsFragment> {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun createView(ui: AnkoContext<CommentsFragment>): View = with(ui) {
        relativeLayout {
            createContentView()
            createFloatingActionButton()
            lparams(matchParent, matchParent)
        }
    }

    private fun _RelativeLayout.createContentView() {
       swipeRefreshLayout {
            isRefreshing = true
            setOnSwipe()
            createRecycleView()
        }.lparams(matchParent, matchParent)
    }

    private fun SwipeRefreshLayout.createRecycleView() {
        recyclerView = recyclerView {
            id = R.id.booru_content_gallery
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            viewModel.getRecycleViewAdapter {
                runOnUi {
                    adapter = it
                    isRefreshing = false
                }
            }
            setOnScroll(linearLayoutManager, this@createRecycleView)
            lparams(matchParent, matchParent)
        }
    }

    @SuppressLint("NewApi")
    private fun _RelativeLayout.createFloatingActionButton() {
        floatingActionButton = floatingActionButton {
            visibility = View.GONE
            id = R.id.booru_content_gallery_fab
            setImageResource(viewModel.appSettings.getStyle().iconArrowUp)
            forLollipop {
                elevation = dip(4).toFloat()
            }
            setOnClickListener {
                if (this@CommentsFragmentUI::recyclerView.isInitialized) {
                    recyclerView.smoothScrollToPosition(0)
                }
            }
        }.lparams {
            alignParentBottom()
            alignParentRight()
            setMargins(0, 0, dip(20), dip(20))
        }
    }

    private fun SwipeRefreshLayout.setOnSwipe() {
        onSwipe {
            if (this@CommentsFragmentUI::recyclerView.isInitialized) {
                viewModel.newRecycleViewAdapter {
                    runOnUi {
                        recyclerView.adapter = it
                        recyclerView.scrollToPosition(0)
                        isRefreshing = false
                        Toasty.success(context, context.getString(R.string.updated)).show()
                    }
                }
            }
        }
    }

    private fun _RecyclerView.setOnScroll(linearLayoutManager: LinearLayoutManager, parent: SwipeRefreshLayout) {
        var isUpdating = false
        onScroll { _, _, _ ->
            if (!this@CommentsFragmentUI::floatingActionButton.isInitialized) return@onScroll

            if (linearLayoutManager.findFirstVisibleItemPosition() >= 3 &&
                    linearLayoutManager.findLastVisibleItemPosition() < adapter.itemCount - 1) {
                floatingActionButton.show()
            } else {
                floatingActionButton.hide()
                if (linearLayoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    if (!isUpdating) {
                        isUpdating = true
                        parent.isRefreshing = true
                        viewModel.updateAdapter {
                            runOnUi {
                                swapAdapter(it, false)
                                parent.isRefreshing = false
                                isUpdating = false
                            }
                        }
                    }
                }
            }
        }
    }
}