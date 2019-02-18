package com.makentoshe.booruchan.postsamples.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostSamplesContentViewModel
import com.makentoshe.booruchan.postsamples.model.AdapterBuilder
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.viewPager

class PostSamplesContentUi(
    private val viewModel: PostSamplesContentViewModel,
    private val adapterBuilder: AdapterBuilder) : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        viewPager {
            id = R.id.postsamples_content_viewpager
            adapter = adapterBuilder.getViewPagerAdapter(ui.owner.childFragmentManager)
            currentItem = viewModel.position

            viewModel.onStartDownloadControllerListener {
                viewModel.startFileDownload(currentItem, context)
            }
        }
    }
}

