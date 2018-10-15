package com.makentoshe.booruchan.sample.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.support.design.chip.Chip
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.*
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.sample.SampleViewModel
import com.makentoshe.booruchan.sample.model.ViewPagerAdapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.viewPager

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
                backgroundColorResource = style.toolbar.primaryColorRes
                title = ""
                setNavigationIcon(style.avdFromMenuToCross)
                navigationIcon?.setColorFilter(
                        ContextCompat.getColor(context, style.toolbar.onPrimaryColorRes),
                        PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

    class BackdropViewBuilder(private val style: Style, private val viewModel: SampleViewModel)
        : ViewBuilder<BackdropImpl> {

        override fun build(ui: AnkoContext<SampleActivity>): BackdropImpl = with(ui.ctx) {
            return backdrop {
                setGravity(Gravity.BOTTOM)
                panelHeight = dip(style.toolbar.dpHeight)
                coveredFadeColor = ContextCompat.getColor(context, android.R.color.transparent)
                backgroundColorResource = style.backdrop.primaryColorRes
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
                    val source = textDataView(context.getString(R.string.source))
                    separate()
                    val chippedTags = ChippedTagsViewBuilder(style)
                    addView(chippedTags.build(ui))
                    separate()

                    viewModel.setPostObserver(ui.owner) {
                        id.text = StringBuilder(context.getString(R.string.id)).append(" ")
                                .append(it.id)
                        time.text = StringBuilder(context.getString(R.string.time)).append(" ")
                                .append(viewModel.booru.convertLocalTimeToDefault(it.createdAt))
                        rating.text = StringBuilder(context.getString(R.string.rating)).append(" ")
                                .append(it.rating)
                        source.text = StringBuilder(context.getString(R.string.source)).append(" ")
                                .append(it.source)
                        chippedTags.update(it.tags)
                    }
                }
            }
        }

        private fun _LinearLayout.textDataView(text: String): TextView {
            return textView {
                this.text = text
                textColor = ContextCompat.getColor(context, style.backdrop.onPrimaryColorRes)
                linksClickable = true
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.MARQUEE
            }.lparams { setMargins(dip(16), 0, 0, 0) }
        }

        private fun _LinearLayout.tagsDataView(text: String): LinearLayout {
            lateinit var view: LinearLayout
            verticalLayout {
                textView(text) {
                    textColor = ContextCompat.getColor(context, style.backdrop.onPrimaryColorRes)
                }.lparams { setMargins(dip(16), 0, 0, dip(8)) }

                horizontalScrollView {
                    view = linearLayout { }
                }.lparams { setMargins(dip(16), 0, 0, dip(16)) }
            }
            return view
        }

        private fun _LinearLayout.separate() {
            cardView {
                radius = dip(1).toFloat()
                backgroundColorResource = style.chip.secondaryColorRes
            }.lparams(Int.MAX_VALUE, dip(2)) {
                setMargins(dip(16), 0, 0, dip(16))
            }
        }

        private fun LinearLayout.addTagsToTagsDataView(tags: Array<String>) {
            removeAllViews()
            for (tag in tags) {
                addView(Chip(context).apply { text = tag })
            }
        }
    }

    class BackdropPanelViewBuilder(private val style: Style, private val viewModel: SampleViewModel)
        : ViewBuilder<View> {

        @SuppressLint("NewApi")
        override fun build(ui: AnkoContext<SampleActivity>): View = with(ui.ctx) {
            val colors = arrayOf(
                    ContextCompat.getColor(this, android.R.color.transparent),
                    ContextCompat.getColor(this, style.view.primaryColorRes))
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

    class ChippedTagsViewBuilder(private val style: Style) : ViewBuilder<View> {

        private lateinit var container: LinearLayout

        override fun build(ui: AnkoContext<SampleActivity>): View = with(ui.ctx) {
            verticalLayout {
                textView(R.string.tags) {
                    textColor = ContextCompat.getColor(context, style.backdrop.onPrimaryColorRes)
                }.lparams { setMargins(dip(16), 0, 0, dip(8)) }

                horizontalScrollView {
                    container = linearLayout { }
                }.lparams { setMargins(dip(16), 0, 0, dip(16)) }
            }
        }

        fun update(tags: Array<String>) {
            tags.forEachIndexed { i, tag ->
                val view = container.getChildAt(i)
                if (view == null) {
                    container.addView(createChip(tag))
                } else {
                    (view as Chip).chip.apply {
                        text = tag
                        isChecked = false
                    }
                }
            }
            if (container.childCount > tags.size) {
                for (i in tags.size until container.childCount step 1) {
                    container.getChildAt(i)?.visibility = View.GONE
                }
            }
        }

        private fun createChip(tag: String) = Chip(container.context).apply {
            chip.text = tag
//            chip.backgroundDrawable?.
//                    setColorFilter(style.chip.getSecondaryColor(context), PorterDuff.Mode.SRC_ATOP)
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { chip, checked ->
                println("$tag\t$checked")
            }
        }

        private class Chip(context: Context) : FrameLayout(context) {

            @JvmField val chip = android.support.design.chip.Chip(context)

            init {
                addView(chip)
                setPadding(0, 0, dip(8), 0)
            }

        }

    }
}