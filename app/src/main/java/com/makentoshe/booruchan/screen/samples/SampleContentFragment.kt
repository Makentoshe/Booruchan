package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.samples.model.SampleVerticalViewPagerAdapter
import com.makentoshe.booruchan.screen.samples.view.SampleContentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onPageChangeListener
import java.io.Serializable

class SampleContentFragment : Fragment() {

    private val router = Booruchan.INSTANCE.router

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
        //setup adapter for creating a cool gesture swipe move
        viewpager.adapter = SampleVerticalViewPagerAdapter(childFragmentManager, position, booru, tags)
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