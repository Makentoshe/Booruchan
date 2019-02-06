package com.makentoshe.booruchan.postpreviews.view

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.animations.OverflowToCrossAnimator
import com.makentoshe.booruchan.postpreviews.animations.OverflowToMagnifyAnimator
import com.makentoshe.booruchan.postpreviews.animations.ToolbarHideElevationAnimator
import com.makentoshe.booruchan.postpreviews.animations.ToolbarShowElevationAnimator
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import org.jetbrains.anko.*

class PostsFragmentUiToolbar(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            id = R.id.postpreview_toolbar_container
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(4).toFloat()
            createDrawerMenuView()
            createToolbarView()
            val overflowImageView =
                createOverflowView().findViewById<ImageView>(R.id.postpreview_toolbar_container_overflow_icon)
            addOverflowListener(overflowImageView)
        }
    }

    private fun _RelativeLayout.createDrawerMenuView() = frameLayout {
        id = R.id.postpreview_toolbar_container_drawermenu
        setOnClickListener(::onDrawerMenuIconClick)
        imageView {
            setToolbarIcon(style, style.drawable.static.menu)
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }
    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun onDrawerMenuIconClick(ignored: View) = postsFragmentViewModel.clickDrawerMenuIcon()

    private fun _RelativeLayout.createToolbarView() = toolbar {
        id = R.id.postpreview_toolbar_container_toolbar
        title = postsFragmentViewModel.booruTitle
        subtitleResource = R.string.posts
        setTitleTextColor(style.toolbar.getOnPrimaryColor(context))
        setBackgroundResource(style.toolbar.primaryColorRes)
        setSubtitleTextColor(style.toolbar.getOnPrimaryColor(context))
    }.lparams(width = matchParent) {
        alignWithParent = true
        rightOf(R.id.postpreview_toolbar_container_drawermenu)
    }

    private fun _RelativeLayout.createOverflowView() = frameLayout {
        setOnClickListener(::onOverflowIconClick)
        id = R.id.postpreview_toolbar_container_overflow

        imageView {
            id = R.id.postpreview_toolbar_container_overflow_icon
            setToolbarIcon(style, style.drawable.static.magnify)
        }.lparams(width = dip(24), height = dip(24)) {
            gravity = Gravity.CENTER
        }

    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun onOverflowIconClick(ignored: View) = postsFragmentViewModel.clickOverflowIcon()

    private fun _RelativeLayout.addOverflowListener(overflowImageView: ImageView) {
        postsFragmentViewModel.addOnOverflowStateChangedListener {
            onTransition {
                when (it.finishState) {
                    OverflowController.OverflowState.Magnify -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                        OverflowToMagnifyAnimator(overflowImageView, style).animate()
                        ToolbarShowElevationAnimator(this@addOverflowListener, it.transitionDuration).animate()
                    }
                    OverflowController.OverflowState.Cross -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                        OverflowToCrossAnimator(overflowImageView, style).animate()
                        ToolbarHideElevationAnimator(this@addOverflowListener, it.transitionDuration).animate()
                    }
                }
            }
        }
    }
}
