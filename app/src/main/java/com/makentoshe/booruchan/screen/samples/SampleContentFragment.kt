package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.VerticalViewPagerAdapter
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.sampleinfo.SampleInfoScreen
import com.makentoshe.booruchan.screen.samples.view.SampleContentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.koin.android.ext.android.inject
import java.io.Serializable

class SampleContentFragment : Fragment() {

    private val router: Router by inject()

    //displaying starts from current position
    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleContentUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewpager = view.find<ViewPager>(R.id.samples_container)
        //create screen which wll be used in adapter
        val screen = object : Screen() {
            override val fragment: Fragment
                get() = SampleFragment.create(position, booru, tags)
        }
        //setup adapter for creating a cool gesture swipe move
        viewpager.adapter = VerticalViewPagerAdapter(childFragmentManager, screen)
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
        //on bottom navigation bar click the item
        view.find<BottomNavigationView>(R.id.samples_bottombar).setOnNavigationItemSelectedListener {
            //try to find viewpager or return false
            //the current item value contains the post number that we need
            val horizontalviewpager = view.findOptional<ViewPager>(R.id.samples_container_viewpager)
                ?: return@setOnNavigationItemSelectedListener false
            //create screen with the selected params and navigate to it
            val screen = SampleInfoScreen(it.itemId, booru, tags, horizontalviewpager.currentItem)
            router.navigateTo(screen)
            return@setOnNavigationItemSelectedListener true
        }
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(
            position: Int,
            booru: Booru,
            tags: Set<Tag>
        ) = SampleContentFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}

