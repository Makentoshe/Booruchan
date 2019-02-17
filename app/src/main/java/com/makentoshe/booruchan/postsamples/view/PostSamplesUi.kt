package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostSamplesViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.onPageChangeListener


class PostSamplesUi(
    private val viewModel: PostSamplesViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            PostSamplesUiToolbar(viewModel, style).createView(AnkoContext.createDelegate(this))
            verticalViewPager {
                id = R.id.postsamples_verticalpager
//                adapter = viewModel.getSamplesVerticalViewPagerAdapter(ui.owner.childFragmentManager)
                currentItem = 1

                onPageChangeListener {
                    onPageScrolled { position, positionOffset, _ ->
                        when (position) {
                            1 -> this@relativeLayout.alpha = 1f
                            0 -> {
                                if (positionOffset == 0f) viewModel.exit()
                                this@relativeLayout.alpha = positionOffset
                            }
                        }
                    }
                }

            }.lparams(matchParent, matchParent) {
                below(R.id.postsamples_toolbar)
                above(R.id.postsamples_bottombar)
            }
            PostSamplesUiBottombar(viewModel, style).createView(AnkoContext.createDelegate(this))
        }
    }

    private fun ViewManager.verticalViewPager(init: @AnkoViewDslMarker VerticalViewPager.() -> Unit): VerticalViewPager {
        return ankoView({ VerticalViewPager(it) }, 0, init)
    }
}