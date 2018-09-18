package com.makentoshe.booruchan.booru.model.gallery.posts

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.model.gallery.GalleryViewModel
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PostOrderedInfinityGallery(private val viewModel: PostOrderedInfinityViewModel,
                                 private val appSettings: AppSettings) : Gallery {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun createView(context: @AnkoViewDslMarker _FrameLayout, galleryViewModel: GalleryViewModel)
            : View = with(context) {
        relativeLayout {
            createGalleryView(this)
            createFloatingActionButton(this)
        }.lparams(matchParent, matchParent)
    }

    override fun onSearchStarted(): (String?) -> (Unit) {
        return {
            if (this@PostOrderedInfinityGallery::recyclerView.isInitialized) {
                recyclerView.apply {
                    adapter = viewModel.newGalleryAdapter(it)
                    scrollToPosition(0)
                }
            }
        }
    }

    private fun createGalleryView(rlcontext: @AnkoViewDslMarker _RelativeLayout) {
        with(rlcontext) {
            swipeRefreshLayout {

                setOnRefreshListener {
                    onSearchStarted()
                    this.isRefreshing = false
                }

                recyclerView = recyclerView {
                    id = R.id.booru_content_gallery
                    adapter = viewModel.getGalleryAdapter()
                    val llm = LinearLayoutManager(this.context)
                    layoutManager = llm

                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                            if (this@PostOrderedInfinityGallery::floatingActionButton.isInitialized) {
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
    }


    @SuppressLint("NewApi")
    private fun createFloatingActionButton(rlcontext: @AnkoViewDslMarker _RelativeLayout) {
        with(rlcontext) {
            floatingActionButton = floatingActionButton {
                visibility = View.GONE
                id = R.id.booru_content_gallery_fab
                setImageResource(appSettings.getStyle().iconArrowUp)
                forLollipop {
                    elevation = dip(4).toFloat()
                }

            }.lparams {
                alignParentBottom()
                alignParentRight()
                setMargins(0, 0, dip(20), dip(20))
            }
        }
    }

}