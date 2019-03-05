package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.model.NavigationController
import com.makentoshe.booruchan.postsamples.model.ViewPagerAdapterBuilder
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.onPageChangeListener

class PostSamplesUiContent(
    private val fragmentManager: FragmentManager,
    private val adapterBuilder: ViewPagerAdapterBuilder,
    private val navigationController: NavigationController
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        verticalViewPager {
            id = R.id.postsamples_verticalpager
            adapter = adapterBuilder.buildViewPagerAdapter(fragmentManager)
            currentItem = 1
            setPadding(0, dip(56), 0, dip(56))

            onPageChangeListener {
                onPageScrolled { position, positionOffset, _ ->
                    when (position) {
                        1 -> ui.owner.alpha = 1f
                        0 -> {
                            if (positionOffset == 0f) navigationController.exit()
                            ui.owner.alpha = positionOffset
                        }
                    }
                }
            }

        }.lparams(matchParent, matchParent)
    }


    private fun ViewManager.verticalViewPager(init: @AnkoViewDslMarker VerticalViewPager.() -> Unit): VerticalViewPager {
        return ankoView({ VerticalViewPager(it) }, 0, init)
    }
}