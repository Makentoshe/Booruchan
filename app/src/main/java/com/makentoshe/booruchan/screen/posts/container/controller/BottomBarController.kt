package com.makentoshe.booruchan.screen.posts.container.controller

import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.anko.onPageChangeListener
import org.jetbrains.anko.find

class BottomBarController(
    private val leftController: BottomBarLeftController,
    private val centerController: BottomBarCenterController,
    private val rightController: BottomBarRightController
) {

    fun bindView(view: View) {
        leftController.bindView(view)
        rightController.bindView(view)
        centerController.bindView(view)
    }
}

class BottomBarRightController {
    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
        val bottomright = view.find<View>(R.id.posts_bottombar_right)

        //tags on right icon click listener
        bottomright.setOnClickListener {
            val currItem = viewpager.currentItem
            viewpager.setCurrentItem(currItem + 1, true)
        }
    }
}

class BottomBarCenterController(private val searchController: SearchController) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
        val bottomcenter = view.find<View>(R.id.posts_bottombar_center)

        val centertext = bottomcenter.find<TextView>(R.id.posts_bottombar_center_textview)
        //tags default value text view in center
        centertext.text = "0"

        viewpager.onPageChangeListener {
            onPageSelected {
                //change text when page was scrolled
                centertext.text = it.toString()
            }
        }

        //change bottom bar indicator to the start
        searchController.onSearchStarted {
            centertext.text = "0"
        }
    }
}

class BottomBarLeftController(private val searchController: SearchController) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
        val bottomLeft = view.find<View>(R.id.posts_bottombar_left)

        //if click was performed on first element
        if (viewpager.currentItem == 0) bottomLeft.visibility = View.INVISIBLE
        viewpager.onPageChangeListener {
            onPageSelected {
                //when scrolled to the first page - hide the left chevron
                //else display and setup functional
                bottomLeft.visibility = if (it < 1) {
                    bottomLeft.setOnClickListener(null)
                    View.INVISIBLE
                } else {
                    bottomLeft.setOnClickListener {
                        //on left chevron click
                        val currItem = viewpager.currentItem
                        viewpager.setCurrentItem(currItem - 1, true)
                    }
                    View.VISIBLE
                }
            }
        }

        //hide the left button
        searchController.onSearchStarted {
            bottomLeft.visibility = View.INVISIBLE
        }
    }
}