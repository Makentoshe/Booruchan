package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.Inflator
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onPageChangeListener

class PostsUiBottomBarInflator : Inflator {

    override fun inflate(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
        val leftview = view.find<View>(R.id.posts_bottombar_left)
        val centerview = view.find<View>(R.id.posts_bottombar_center)
        val rightview = view.find<View>(R.id.posts_bottombar_right)

        left(viewpager, leftview)
        center(viewpager, centerview)
        right(viewpager, rightview)
    }

    private fun left(viewPager: ViewPager, view: View) {
        if (viewPager.currentItem == 0) {
            view.visibility = View.INVISIBLE
        }

        viewPager.onPageChangeListener {
            onPageSelected {
                if (it < 1) {
                    view.visibility = View.INVISIBLE
                    view.setOnClickListener(null)
                } else {
                    view.visibility = View.VISIBLE
                    view.onClick {
                        val currItem = viewPager.currentItem
                        viewPager.setCurrentItem(currItem - 1, true)
                    }
                }
            }
        }
    }

    private fun center(viewPager: ViewPager, view: View) {
        val textview = view.find<TextView>(R.id.posts_bottombar_center_textview)
        textview.text = "0"

        viewPager.onPageChangeListener {
            onPageSelected {
                textview.text = it.toString()
            }
        }
    }

    private fun right(viewPager: ViewPager, view: View) {
        view.onClick {
            val currItem = viewPager.currentItem
            viewPager.setCurrentItem(currItem + 1, true)
        }
    }
}