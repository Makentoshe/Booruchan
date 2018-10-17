package com.makentoshe.booruchan.booru.content.posts.infinity.ordered.view

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.content.posts.infinity.ordered.PostsViewModel
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.onScroll
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PostsFragmentUI(private val viewModel: PostsViewModel) : AnkoComponent<PostsFragment> {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun createView(ui: AnkoContext<PostsFragment>): View = with(ui) {
        relativeLayout {
            createGalleryView()
            createFloatingActionButton()
            lparams(matchParent, matchParent)
        }
    }

    private fun _RelativeLayout.createGalleryView() {
        swipeRefreshLayout {
            onRefresh {
                if (this@PostsFragmentUI::recyclerView.isInitialized) {
                    recyclerView.apply {
                        adapter = viewModel.newGalleryAdapter(viewModel.getSearchTerm())
                        scrollToPosition(0)
                    }
                }
                this@swipeRefreshLayout.isRefreshing = false
                Toasty.success(context, context.getString(R.string.updated)).show()
            }

            recyclerView = recyclerView {
                id = R.id.booru_content_gallery
                adapter = viewModel.getGalleryAdapter()
                val llm = LinearLayoutManager(this.context)
                layoutManager = llm

                onScroll { v, dx, dy ->
                    if (this@PostsFragmentUI::floatingActionButton.isInitialized) {
                        if (llm.findFirstVisibleItemPosition() >= 3) {
                            floatingActionButton.show()
                        } else {
                            floatingActionButton.hide()
                        }
                    }
                }
                lparams(matchParent, matchParent)
            }
        }.lparams(matchParent, matchParent)
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
                if (this@PostsFragmentUI::recyclerView.isInitialized) {
                    recyclerView.smoothScrollToPosition(0)
                }
            }
        }.lparams {
            alignParentBottom()
            alignParentRight()
            setMargins(0, 0, dip(20), dip(20))
        }
    }
}