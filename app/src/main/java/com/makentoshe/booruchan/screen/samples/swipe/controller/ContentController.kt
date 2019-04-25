package com.makentoshe.booruchan.screen.samples.swipe.controller

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.anko.onPageChangeListener
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.VerticalViewPagerAdapter
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.samples.container.SampleFragment
import org.jetbrains.anko.find
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Controls a vertical viewpager behaviour.
 */
class ContentController(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int,
    private val fragmentManager: FragmentManager
) : KoinComponent {

    private val router by inject<Router>()

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.samples_container)
        //create screen which wll be used in adapter
        val screen = sampleScreenBuild()
        //setup adapter for creating a cool gesture swipe move
        viewpager.adapter = VerticalViewPagerAdapter(fragmentManager, screen)
        //show content fragment as a default
        viewpager.currentItem = 1
        //when drag event occurs the alpha will be decreased proportionally offset value
        //when offset equals 0 - the current screen is fully hiding and we can call exit()
        viewpager.onPageChangeListener {
            onPageScrolled { position, offset, _ ->
                if (position == 0) {
                    view.alpha = offset
                    if (offset == 0f) router.exit()
                }
            }
        }
    }

    /**
     * Creates a screen with main content - a horizontal viewpager
     * that is contains images and performs horizontal swipe to scroll them.
     */
    private fun sampleScreenBuild() = object: Screen() {
        override val fragment: Fragment
            get() = SampleFragment.create(position, booru, tags)
    }
}