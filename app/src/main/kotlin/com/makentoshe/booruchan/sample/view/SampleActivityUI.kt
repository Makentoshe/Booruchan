package com.makentoshe.booruchan.sample.view

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.*
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.sample.SampleViewModel
import com.makentoshe.booruchan.sample.model.ViewPagerAdapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.viewPager

class SampleActivityUI(style: Style, private val viewModel: SampleViewModel)
    : StyleableAnkoComponent<SampleActivity>(style) {

    override fun createView(ui: AnkoContext<SampleActivity>): View = with(ui) {
        verticalLayout {
//            addView(BannerViewBuilder2Lines2ActionsImageImpl(style, this).build(ui))
            val actionBar = ActionBarBuilder(style).build(ui).setSupportActionBar(ui.owner)
            addView(actionBar)
            val backdrop = BackdropViewBuilder(style, viewModel).build(ui)
            addView(backdrop)
            viewModel.setupActionBarController(actionBar, backdrop, style)
        }
    }

    interface ViewBuilder<View> {
        fun build(ui: AnkoContext<SampleActivity>): View
    }

    class ActionBarBuilder(private val style: Style) : ViewBuilder<Toolbar> {

        override fun build(ui: AnkoContext<SampleActivity>): Toolbar = with(ui.ctx) {
            return toolbar {
                backgroundColorResource = style.toolbarBackgroundColor
                title = ""
                setNavigationIcon(style.avdFromMenuToCross)
                navigationIcon?.setColorFilter(
                        ContextCompat.getColor(context, style.toolbarForegroundColor),
                        PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

    class BackdropViewBuilder(private val style: Style, private val viewModel: SampleViewModel)
        : ViewBuilder<BackdropImpl> {

        override fun build(ui: AnkoContext<SampleActivity>): BackdropImpl = with(ui.ctx) {
            return backdrop {
                setGravity(Gravity.BOTTOM)
                panelHeight = dip(style.dpToolbarHeight)
                coveredFadeColor = ContextCompat.getColor(context, android.R.color.transparent)
                backgroundColorResource = style.toolbarBackgroundColor
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                isTouchEnabled = false

                addView(BackdropContentViewBuilder(style, viewModel).build(ui))
                addView(BackdropPanelViewBuilder(style, viewModel).build(ui))
            }
        }
    }

    class BackdropContentViewBuilder(private val style: Style, private val viewModel: SampleViewModel)
        : ViewBuilder<View> {

        @SuppressLint("NewApi")
        override fun build(ui: AnkoContext<SampleActivity>): View = with(ui.ctx) {
            return scrollView {
                verticalLayout {

                    val id = textDataView(context.getString(R.string.id))
                    separate()
                    val time = textDataView(context.getString(R.string.time))
                    separate()
                    val rating = textDataView(context.getString(R.string.rating))
                    separate()

                    viewModel.setPostObserver(ui.owner) {
                        id.text = StringBuilder(context.getString(R.string.id)).append(" ")
                                .append(it.id)
                        time.text = StringBuilder(context.getString(R.string.time)).append(" ")
                                .append(viewModel.booru.convertLocalTimeToDefault(it.createdAt))
                        rating.text = StringBuilder(context.getString(R.string.rating)).append(" ")
                                .append(it.rating)
                    }
                }
            }
        }

        private fun _LinearLayout.textDataView(text: String): TextView {
            return textView {
                this.text = text
                textColor = ContextCompat.getColor(context, style.toolbarForegroundColor)
            }.lparams { setMargins(dip(16), 0, 0, 0) }
        }

        private fun _LinearLayout.separate() {
            cardView {
                radius = dip(1).toFloat()
                backgroundColorResource = style.hintColor
            }.lparams(Int.MAX_VALUE, dip(2)) {
                setMargins(dip(16), 0, 0, dip(16))
            }
        }
    }

    class BackdropPanelViewBuilder(private val style: Style, private val viewModel: SampleViewModel)
        : ViewBuilder<View> {

        @SuppressLint("NewApi")
        override fun build(ui: AnkoContext<SampleActivity>): View = with(ui.ctx) {
            val colors = arrayOf(
                    ContextCompat.getColor(this, android.R.color.transparent),
                    ContextCompat.getColor(this, style.backgroundColor))
            val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors.toIntArray())

            verticalLayout {
                backgroundDrawable = gradient
                layoutParams = LinearLayout.LayoutParams(matchParent, matchParent)
                forLollipop { elevation = 0f }
                cardView {
                    radius = dip(16).toFloat()
                    layoutParams = ViewGroup.LayoutParams(matchParent, getScreenSize(context).height)

                    viewPager {
                        id = R.id.sample_backdrop_sliding_viewpager
                        adapter = ViewPagerAdapter(ui.owner.supportFragmentManager, viewModel)
                        onPageChangeListener { onPageSelected { viewModel.setCurrentPage(currentItem) } }
                        currentItem = viewModel.pageId
                    }
                }
            }
        }

    }

    abstract class BannerViewBuilder2Lines2ActionsImage(private val style: Style) : ViewBuilder<View> {

        override fun build(ui: AnkoContext<SampleActivity>): View = with(ui.ctx) {
            verticalLayout {
                linearLayout {
                    createImageView()
                    textView(getTextMessage()) {
                        textSize = 12f
                        maxLines = 2
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        setMargins(dip(16), 0, dip(8), 0)
                    }
                }
                linearLayout {
                    gravity = Gravity.END
                    createActionButton(getFirstAction(), this@verticalLayout)
                    createActionButton(getSecondAction(), this@verticalLayout)
                }
                lparams(width = matchParent) {
                    setMargins(0, dip(24), dip(8), dip(8))
                }
            }
        }

        private fun _LinearLayout.createActionButton(action: Action, banner: View) {
            button(action.text) {
                backgroundColorResource = android.R.color.transparent
                onClick { action.action(banner) }
            }.lparams { setMargins(dip(8), 0, 0, 0) }
        }

        private fun _LinearLayout.createImageView() {
            if (getImageResource() != -1 || getImageDrawable() != null) {
                imageView {
                    if (getImageResource() != -1) {
                        imageResource = getImageResource()
                    }
                    getImageDrawable()?.let { setImageDrawable(it) }
                }.lparams(dip(40), dip(40)) {
                    setMargins(dip(16), 0, 0, 0)
                }
            }
        }

        abstract fun getFirstAction(): Action

        abstract fun getSecondAction(): Action

        abstract fun getTextMessage(): String

        open fun getImageResource(): Int = -1

        open fun getImageDrawable(): Drawable? = null

        data class Action(@JvmField val text: String, @JvmField val action: (View) -> Unit)
    }

    class BannerViewBuilder2Lines2ActionsImageImpl(style: Style, private val parent: ViewGroup)
        : BannerViewBuilder2Lines2ActionsImage(style) {

        override fun getTextMessage(): String {
            return "Two line text string with two actions. One to two lines is preferable on mobile"
        }

        override fun getFirstAction(): Action {
            return Action("Details") {
                println("Show details")
            }
        }

        override fun getSecondAction(): Action {
            return Action("Dismiss") {
                println("Dismiss banner ")
                it.post {
                    it.visibility = View.GONE
                    this.parent.removeView(it)
                }
            }
        }

        override fun getImageResource(): Int = R.drawable.ic_launcher_background

    }

}