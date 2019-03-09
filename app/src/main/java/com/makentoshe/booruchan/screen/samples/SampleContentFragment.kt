package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.samples.model.SampleVerticalViewPagerAdapter
import com.makentoshe.booruchan.screen.samples.view.SampleContentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onPageChangeListener

class SampleContentFragment : Fragment() {

    private val router = Booruchan.INSTANCE.router

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleContentUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewpager = view.find<ViewPager>(R.id.samples_container)
        viewpager.adapter = SampleVerticalViewPagerAdapter(childFragmentManager, position)
        viewpager.currentItem = 1
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
        fun create(position: Int) = SampleContentFragment().apply {
            this.position = position
        }
    }
}