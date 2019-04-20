package com.makentoshe.booruchan.screen.posts.controller

import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchStateViewModel
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onPageChangeListener

class BottomBarController(private val searchStateViewModel: SearchStateViewModel) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)

        val bottomleft = view.find<View>(R.id.posts_bottombar_left)
        val bottomcenter = view.find<View>(R.id.posts_bottombar_center)
        val bottomright = view.find<View>(R.id.posts_bottombar_right)

        bindLeft(bottomleft, viewpager)
        bindRight(bottomright, viewpager)
        bindCenter(bottomcenter, viewpager)

    }

    private fun bindCenter(view: View, viewpager: ViewPager) {
        val centertext = view.find<TextView>(R.id.posts_bottombar_center_textview)
        //set default value text view in center
        centertext.text = "0"

        viewpager.onPageChangeListener {
            onPageSelected {
                //change text when page was scrolled
                centertext.text = it.toString()
            }
        }

        //change bottom bar indicator to the start
        searchStateViewModel.onSearchStarted {
            centertext.text = "0"
        }
    }

    private fun bindRight(view: View, viewpager: ViewPager) {
        //set on right icon click listener
        view.setOnClickListener {
            val currItem = viewpager.currentItem
            viewpager.setCurrentItem(currItem + 1, true)
        }
    }

    private fun bindLeft(view: View, viewpager: ViewPager) {
        //if click was performed on first element
        if (viewpager.currentItem == 0) view.visibility = View.INVISIBLE
        viewpager.onPageChangeListener {
            onPageSelected {
                //when scrolled to the first page - hide the left chevron
                //else display and setup functional
                view.visibility = if (it < 1) {
                    view.setOnClickListener(null)
                    View.INVISIBLE
                } else {
                    view.setOnClickListener {
                        //on left chevron click
                        val currItem = viewpager.currentItem
                        viewpager.setCurrentItem(currItem - 1, true)
                    }
                    View.VISIBLE
                }
            }
        }

        //hide the left button
        searchStateViewModel.onSearchStarted {
            view.visibility = View.INVISIBLE
        }
    }
}