package com.makentoshe.booruchan.screen.samples.swipe.controller

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.sampleinfo.SampleInfoScreen
import org.jetbrains.anko.find
import org.jetbrains.anko.findOptional
import org.koin.core.KoinComponent
import org.koin.core.inject

class BottomBarController(
    private val booru: Booru,
    private val tags: Set<Tag>
) : KoinComponent {

    private val router by inject<Router>()

    fun bindView(view: View) {
        val bottomNavigationView = view.find<BottomNavigationView>(R.id.samples_bottombar)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            //try to find viewpager or return false
            //the current item value contains the post number that we need
            val horizontalviewpager = view.findOptional<ViewPager>(R.id.samples_container_viewpager)
                ?: return@setOnNavigationItemSelectedListener false
            //create screen with the selected params and navigate to it
            val screen = buildSampleInfoScreen(it.itemId, horizontalviewpager.currentItem)
            router.navigateTo(screen)
            return@setOnNavigationItemSelectedListener true
        }
    }

    /*
    * screenType - is an navigation menu item id. (info, tags, comments, etc)
    * */
    private fun buildSampleInfoScreen(screenType: Int, position: Int): Screen {
        return SampleInfoScreen(screenType, booru, tags, position)
    }
}