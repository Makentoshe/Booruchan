package com.makentoshe.booruchan.booru.view.content.comments

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.content.comments.CommentsContentViewModel
import com.makentoshe.booruchan.common.forLollipop
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CommentsFragmentUI(private val viewModel: CommentsContentViewModel): AnkoComponent<CommentsFragment> {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun createView(ui: AnkoContext<CommentsFragment>): View = with(ui) {
        relativeLayout {
            createGalleryView()
            createFloatingActionButton()
            lparams(matchParent, matchParent)
        }
    }

    private fun _RelativeLayout.createGalleryView() {
        swipeRefreshLayout {
            setOnRefreshListener {
                if (this@CommentsFragmentUI::recyclerView.isInitialized) {
                    recyclerView.apply {
                        adapter = viewModel.newGalleryAdapter()
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

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                        if (this@CommentsFragmentUI::floatingActionButton.isInitialized) {
                            if (llm.findFirstVisibleItemPosition() >= 3) {
                                floatingActionButton.show()
                            } else {
                                floatingActionButton.hide()
                            }
                        }
                    }
                })
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
}