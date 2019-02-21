package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostSamplesViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.onPageChangeListener

class PostSamplesUiContent(
    private val fragmentManager: FragmentManager,
    private val viewModel: PostSamplesViewModel
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        verticalViewPager {
            id = R.id.postsamples_verticalpager
            adapter = viewModel.getSamplesVerticalViewPagerAdapter(fragmentManager)
            currentItem = 1

            onPageChangeListener {
                onPageScrolled { position, positionOffset, _ ->
                    when (position) {
                        1 -> ui.owner.alpha = 1f
                        0 -> {
                            if (positionOffset == 0f) viewModel.exit()
                            ui.owner.alpha = positionOffset
                        }
                    }
                }
            }

        }.lparams(matchParent, matchParent) {
            below(R.id.postsamples_toolbar)
            above(R.id.postsamples_bottombar)
        }
    }


    private fun ViewManager.verticalViewPager(init: @AnkoViewDslMarker VerticalViewPager.() -> Unit): VerticalViewPager {
        return ankoView({ VerticalViewPager(it) }, 0, init)
    }
}