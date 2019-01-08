package com.makentoshe.booruchan.posts.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.viewPager

class PostsFragmentUiContentViewpager(
    private val viewModel: PostsFragmentViewModel,
    private val fragmentManager: FragmentManager
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) {
                below(R.id.toolbar_container)
                above(R.id.bottombar)
            }
            viewPager {
                id = R.id.content_viewpager
                viewModel.onNewSearchStarted {
                    adapter = viewModel.getViewPagerAdapter(fragmentManager, it)
                    viewModel.viewPagerController.gotoPage(0)
                }

                onPageChangeListener {
                    onPageSelected {
                        viewModel.viewPagerController.gotoPage(it)
                    }
                }

                viewModel.viewPagerController.onPageGoto {
                    if (currentItem < it) setCurrentItem(it, true)
                    else {
                        while (it != currentItem) {
                            setCurrentItem(currentItem - 1, true)
                        }
                    }
                }

            }
        }
    }
}