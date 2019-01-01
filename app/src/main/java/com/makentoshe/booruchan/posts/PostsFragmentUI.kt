package com.makentoshe.booruchan.posts

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.view.View
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.posts.animations.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.subtitleResource
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager

class PostsFragmentUI(private val postsFragmentViewModel: PostsFragmentViewModel) : AnkoComponent<PostsFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<PostsFragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            createToolbar()
            createContent()
        }
    }

    private fun _RelativeLayout.createToolbar() {
        relativeLayout {
            id = R.id.poststoolbar
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(4).toFloat()
            toolbarLayout({
                title = postsFragmentViewModel.booru.title
                subtitleResource = R.string.posts
                setTitleTextColor(style.toolbar.getOnPrimaryColor(context))
                setBackgroundResource(style.toolbar.primaryColorRes)
                setSubtitleTextColor(style.toolbar.getOnPrimaryColor(context))
            }, {
                id = R.id.overflow
                setImageResource(style.drawable.static.magnify)
                setColorFilter(style.toolbar.getOnPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
                setOnClickListener(::onOverflowIconClick)
                postsFragmentViewModel.uiController.addOverflowListener {
                    onTransition {
                        when (it.finishState) {
                            OverflowState.Magnify -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                                OverflowToMagnifyAnimator(this@toolbarLayout, style).animate()
                                ToolbarShowElevationAnimator(this@relativeLayout, it.transitionDuration).animate()
                            }
                            OverflowState.Cross -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                                OverflowToCrossAnimator(this@toolbarLayout, style).animate()
                                ToolbarHideElevationAnimator(this@relativeLayout, it.transitionDuration).animate()
                            }
                        }
                        postsFragmentViewModel.launch {
                            delay(it.transitionDuration)
                            postsFragmentViewModel.uiController.overflowController.newState(it.finishState)
                        }
                    }
                }
            })
        }.lparams(width = matchParent)
    }

    private fun onOverflowIconClick(ignored: View) {
        postsFragmentViewModel.uiController.action(Action.UIAction.OverflowClick)
    }

    private fun _RelativeLayout.createContent() {
        relativeLayout {
            createCover()
            createSearch()
            viewPager {
                backgroundColor = Color.CYAN
            }.lparams(matchParent, matchParent) {
                below(R.id.poststoolbar)
                above(R.id.postsnavigation)
            }
            bottomNavigation()
        }.lparams(matchParent, matchParent) {
            below(R.id.poststoolbar)
        }
    }

    private fun _RelativeLayout.createCover() {
        frameLayout {
            visibility = View.GONE
            alpha = 0f
            backgroundColor = Color.argb(150, 0, 0, 0)
            postsFragmentViewModel.uiController.addOverflowListener {
                onTransition {
                    when (it.finishState) {
                        OverflowState.Cross -> CoverShowAnimator(this@frameLayout, it.transitionDuration).animate()
                        OverflowState.Magnify -> CoverHideAnimator(this@frameLayout, it.transitionDuration).animate()
                    }
                }
            }
            onClick {
                val overflowController = postsFragmentViewModel.uiController.overflowController
                if (overflowController.value == OverflowState.Cross) {
                    overflowController.newState(OverflowState.Transition(OverflowState.Magnify))
                }
            }
        }.lparams(matchParent, matchParent)
    }

    private fun _RelativeLayout.createSearch() {
        relativeLayout {
            elevation = dip(4).toFloat()
            translationY = -(dip(56).toFloat() + elevation)
            backgroundColorResource = style.toolbar.primaryColorRes
            setPadding(dip(8), dip(4), dip(8), dip(8))
            setOnClickListener { /* just handle */ }

            cardView {
                radius = dip(2).toFloat()
                elevation = dip(2).toFloat()

                delayAutoCompleteEditText {
                    setCursorColor(Color.BLACK)
                    postsFragmentViewModel.uiController.addOverflowListener {
                        onTransition {
                            when (it.finishState) {
                                OverflowState.Magnify -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                                    SearchHideAnimator(this@relativeLayout, it.transitionDuration).animate()
                                    this@delayAutoCompleteEditText.clearFocus()
                                    hideKeyboard(context, this@delayAutoCompleteEditText)
                                }
                                OverflowState.Cross -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                                    SearchShowAnimator(this@relativeLayout, it.transitionDuration).animate()
                                    this@delayAutoCompleteEditText.requestFocus()
                                    showKeyboard(context, this@delayAutoCompleteEditText)
                                }
                            }
                        }
                    }
                }.lparams(matchParent, matchParent)

            }.lparams(matchParent, matchParent)

        }.lparams(width = matchParent, height = dip(56)) {
            alignParentTop()
        }
    }

    private fun _RelativeLayout.bottomNavigation() {
        linearLayout {
            id = R.id.postsnavigation
            backgroundColorResource = style.toolbar.primaryColorRes
        }.lparams(width = matchParent, height = dip(56)) {
            alignParentBottom()
        }
    }
}