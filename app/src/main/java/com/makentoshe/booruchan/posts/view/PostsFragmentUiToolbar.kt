package com.makentoshe.booruchan.posts.view

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.posts.model.OverflowState
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.posts.animations.OverflowToCrossAnimator
import com.makentoshe.booruchan.posts.animations.OverflowToMagnifyAnimator
import com.makentoshe.booruchan.posts.animations.ToolbarHideElevationAnimator
import com.makentoshe.booruchan.posts.animations.ToolbarShowElevationAnimator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.*

class PostsFragmentUiToolbar(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            id = R.id.toolbar_container
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(4).toFloat()
            createDrawerMenuView()
            createToolbarView()
            val overflowImageView = createOverflowView().findViewById<ImageView>(R.id.toolbar_container_overflow)
            addOverflowListener(overflowImageView)
        }
    }

    private fun _RelativeLayout.createDrawerMenuView() = frameLayout {
        id = R.id.toolbar_container_drawermenu
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

    private fun onDrawerMenuIconClick(ignored: View) {
        postsFragmentViewModel.uiController.action(Action.UIAction.MenuClick)
    }

    private fun _RelativeLayout.createToolbarView() = toolbar {
        id = R.id.toolbar_container_toolbar
        title = postsFragmentViewModel.booru.title
        subtitleResource = R.string.posts
        setTitleTextColor(style.toolbar.getOnPrimaryColor(context))
        setBackgroundResource(style.toolbar.primaryColorRes)
        setSubtitleTextColor(style.toolbar.getOnPrimaryColor(context))
    }.lparams(width = matchParent) {
        alignWithParent = true
        rightOf(R.id.toolbar_container_drawermenu)
    }

    private fun _RelativeLayout.createOverflowView() = frameLayout {
        setOnClickListener(::onOverflowIconClick)

        imageView {
            id = R.id.toolbar_container_overflow
            setToolbarIcon(style, style.drawable.static.magnify)
        }.lparams(width = dip(24), height = dip(24)) {
            gravity = Gravity.CENTER
        }

    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun onOverflowIconClick(ignored: View) {
        postsFragmentViewModel.uiController.action(Action.UIAction.OverflowClick)
    }

    private fun _RelativeLayout.addOverflowListener(overflowImageView: ImageView) {
        postsFragmentViewModel.uiController.addOverflowListener {
            onTransition {
                when (it.finishState) {
                    OverflowState.Magnify -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                        OverflowToMagnifyAnimator(overflowImageView, style).animate()
                        ToolbarShowElevationAnimator(this@addOverflowListener, it.transitionDuration).animate()
                    }
                    OverflowState.Cross -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                        OverflowToCrossAnimator(overflowImageView, style).animate()
                        ToolbarHideElevationAnimator(this@addOverflowListener, it.transitionDuration).animate()
                    }
                }
                postsFragmentViewModel.launch {
                    delay(it.transitionDuration)
                    postsFragmentViewModel.uiController.overflowController.newState(it.finishState)
                }
            }
        }
    }
}