package com.makentoshe.booruchan.sample.view

import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import org.jetbrains.anko.support.v4.viewPager

class SampleActivityUI(style: Style, private val viewModel: SampleViewModel)
    : StyleableAnkoComponent<SampleActivity>(style) {

    override fun createView(ui: AnkoContext<SampleActivity>): View = with(ui) {
        verticalLayout {
            val actionBar = createActionBar(ui)
            val backdrop = createContent(ui)
            viewModel.setupActionBarController(actionBar, backdrop, style)
        }
    }

    private fun _LinearLayout.createActionBar(ui: AnkoContext<SampleActivity>): Toolbar {
        return toolbar {
            backgroundColorResource = style.toolbarBackgroundColor
            title = ""
            setNavigationIcon(style.avdFromMenuToCross)
            navigationIcon?.setColorFilter(
                    ContextCompat.getColor(context, style.toolbarForegroundColor),
                    PorterDuff.Mode.SRC_ATOP)
        }.setSupportActionBar(ui.owner)
    }

    private fun _LinearLayout.createContent(ui: AnkoContext<SampleActivity>): BackdropView {
        return backdrop {
            setGravity(Gravity.BOTTOM)
            panelHeight = dip(style.dpToolbarHeight)
            coveredFadeColor = ContextCompat.getColor(context, android.R.color.transparent)
            backgroundColorResource = style.toolbarBackgroundColor
            panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            isTouchEnabled = false

            createContentLayout()
            createSlidingLayout(ui)
        }
    }

    private fun SlidingUpPanelLayout.createContentLayout() {
        verticalLayout {

            textView {
                text = "Image data"
                gravity = Gravity.CENTER
                textSize = dip(16).toFloat()
            }
        }
    }

    private fun SlidingUpPanelLayout.createSlidingLayout(ui: AnkoContext<SampleActivity>) {
        val colors = arrayOf(
                ContextCompat.getColor(context, android.R.color.transparent),
                ContextCompat.getColor(context, style.backgroundColor))
        val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors.toIntArray())

        verticalLayout {
            backgroundDrawable = gradient
            layoutParams = LinearLayout.LayoutParams(matchParent, matchParent)

            cardView {
                radius = dip(8).toFloat()
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)


            }
        }
    }

}