package com.makentoshe.booruchan.sample.view

import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.BackdropImpl
import com.makentoshe.booruchan.common.BackdropView
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.backdrop
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.sample.SampleViewModel
import com.makentoshe.booruchan.sample.model.ViewPagerAdapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.viewPager
import java.util.*

class SampleActivityUI(style: Style, private val viewModel: SampleViewModel)
    : StyleableAnkoComponent<SampleActivity>(style) {

    override fun createView(ui: AnkoContext<SampleActivity>): View = with(ui) {
        verticalLayout {
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

        override fun build(ui: AnkoContext<SampleActivity>): View = with(ui.ctx) {
            return scrollView {
                verticalLayout {

                    val id = textView {
                        text = "Image data"
                        gravity = Gravity.CENTER
                        textSize = dip(16).toFloat()
                    }

                    viewModel.setPostObserver(ui.owner) {
                        id.text = Arrays.toString(it.tags)
                    }
                }
            }
        }

    }

    class BackdropPanelViewBuilder(private val style: Style, private val viewModel: SampleViewModel)
        :ViewBuilder<View> {

        override fun build(ui: AnkoContext<SampleActivity>): View = with(ui.ctx) {
            val colors = arrayOf(
                    ContextCompat.getColor(this, android.R.color.transparent),
                    ContextCompat.getColor(this, style.backgroundColor))
            val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors.toIntArray())

            verticalLayout {
                backgroundDrawable = gradient
                layoutParams = LinearLayout.LayoutParams(matchParent, matchParent)

                cardView {
                    radius = dip(8).toFloat()
                    layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

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
}