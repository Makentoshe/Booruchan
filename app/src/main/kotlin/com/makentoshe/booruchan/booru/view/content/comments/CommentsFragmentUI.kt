package com.makentoshe.booruchan.booru.view.content.comments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.content.comments.CommentsContentViewModel
import com.makentoshe.booruchan.common.forLollipop
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CommentsFragmentUI(private val viewModel: CommentsContentViewModel)
    : AnkoComponent<CommentsFragment>, ProgressBarController {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var progressBar: ProgressBar

    override fun createView(ui: AnkoContext<CommentsFragment>): View = with(ui) {
        relativeLayout {
            createProgressBar()
            createGalleryView()
            createFloatingActionButton()
            lparams(matchParent, matchParent)
        }
    }

    private fun _RelativeLayout.createProgressBar() {
        progressBar = horizontalProgressBar {
            isIndeterminate = true
            max = 1
        }.lparams(matchParent, dip(10)) {
            alignParentTop()
        }
    }

    private fun _RelativeLayout.createGalleryView() {
        swipeRefreshLayout {
            setOnRefreshListener {
                if (this@CommentsFragmentUI::recyclerView.isInitialized) {
                    recyclerView.apply {
                        adapter = viewModel.newGalleryAdapter(this@CommentsFragmentUI)
                        scrollToPosition(0)
                    }
                }
                this@swipeRefreshLayout.isRefreshing = false
                Toasty.success(context, context.getString(R.string.updated)).show()
            }

            recyclerView = recyclerView {
                id = R.id.booru_content_gallery
                adapter = viewModel.getGalleryAdapter(this@CommentsFragmentUI)
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
                            if (llm.findLastVisibleItemPosition() >=
                                    viewModel.getGalleryAdapter(this@CommentsFragmentUI).itemCount - 1) {
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

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun errorProgressBar() {
        progressBar.progress = 1
        progressBar.indeterminateDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN)
        progressBar.isIndeterminate = false
    }

}