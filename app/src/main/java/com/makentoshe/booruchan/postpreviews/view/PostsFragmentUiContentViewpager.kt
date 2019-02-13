package com.makentoshe.booruchan.postpreviews.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.model.SearchController
import com.makentoshe.booruchan.postpreviews.model.ViewPagerController
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.viewPager

class PostsFragmentUiContentViewpager(
    private val viewModel: PostsFragmentViewModel,
    private val fragmentManager: FragmentManager,
    private val searchController: SearchController,
    private val viewPagerController: ViewPagerController
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) {
                below(R.id.postpreview_toolbar_container)
                above(R.id.postpreview_bottombar)
            }
            viewPager {
                id = R.id.content_viewpager
                searchController.onSearchStartedListener {
                    adapter = viewModel.getViewPagerAdapter(fragmentManager, it)
                    viewPagerController.gotoPage(0)
                }

                onPageChangeListener {
                    onPageSelected {
                        viewPagerController.gotoPage(it)
                    }
                }

                viewPagerController.onPageChangedListener {
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