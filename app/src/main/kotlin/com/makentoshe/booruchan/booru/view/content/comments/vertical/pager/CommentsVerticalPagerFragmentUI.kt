package com.makentoshe.booruchan.booru.view.content.comments.vertical.pager

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.content.comments.pager.vertical.CommentsContentVerticalPagerAdapter
import com.makentoshe.booruchan.booru.model.content.comments.pager.vertical.CommentsContentVerticalPagerViewModel
import com.makentoshe.booruchan.common.view.FloatingActionNavigationButton
import com.makentoshe.booruchan.common.view.floatingActionNavigationButton
import com.makentoshe.booruchan.common.view.verticalViewPager
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onPageChangeListener

class CommentsVerticalPagerFragmentUI(private val viewModel: CommentsContentVerticalPagerViewModel)
    : AnkoComponent<CommentsVerticalPagerFragment> {

    override fun createView(ui: AnkoContext<CommentsVerticalPagerFragment>): View = with(ui) {
        relativeLayout {
            verticalViewPager {
                id = R.id.booru_comment_viewpager
                adapter = CommentsContentVerticalPagerAdapter(ui.owner.childFragmentManager)
                offscreenPageLimit = 1
                onPageChangeListener {
                    onPageSelected {
                        this@relativeLayout.findViewById<TextView>(R.id.booru_comment_fanb_collapse)
                                .text = (it + 1).toString()
                    }
                }
            }
            floatingActionNavigationButton {
                blockExpand = true
                val color = ContextCompat.getColor(context, viewModel.appSettings.getStyle().toolbarBackgroundColor)
                background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                textView {
                    id = R.id.booru_comment_fanb_collapse
                    gravity = Gravity.CENTER
                    text = "1"
                    textSize = dip(14).toFloat()
                    textColorResource = viewModel.appSettings.getStyle().toolbarForegroundColor
                }
                createDialogOnClick(this@relativeLayout)
            }.lparams(dip(56), dip(56)) {
                alignParentBottom()
                alignParentRight()
                setMargins(0, 0, dip(16), dip(16))
            }
        }
    }

    private fun FloatingActionNavigationButton.createDialogOnClick(mainView: View) {
        onClick {
            val view = context.layoutInflater
                    .inflate(R.layout.comment_verticalpager_scrollto_dialog, null)
            val textView = view.findViewById<TextView>(R.id.dialog_numericedittext)
            val viewPager = mainView.findViewById<ViewPager>(R.id.booru_comment_viewpager)
            textView.hint = (viewPager.currentItem + 1).toString()

            val builder = AlertDialog.Builder(context)
                    .setTitle(R.string.go_to_page)
                    .setCancelable(true)
            builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            builder.setView(view)
            builder.setPositiveButton(R.string.go) { dialog, _ ->
                try {
                    textView.text.toString().toInt().let {
                        if (it > 0) {
                            viewPager.setCurrentItem(it - 1, false)
                        } else {
                            viewPager.setCurrentItem(0, false)
                        }
                    }
                } catch (e: NumberFormatException) {
                    dialog.dismiss()
                }
            }
            builder.create().show()
        }
    }
}