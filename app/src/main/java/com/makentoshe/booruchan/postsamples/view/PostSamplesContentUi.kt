package com.makentoshe.booruchan.postsamples.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostSamplesContentViewModel
import com.makentoshe.booruchan.postsamples.model.AdapterBuilder
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.viewPager

class PostSamplesContentUi(
    private val viewModel: PostSamplesContentViewModel,
    private val adapterBuilder: AdapterBuilder
) : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        viewPager {
            id = R.id.postsamples_content_viewpager

            val fragmentManager = ui.owner.childFragmentManager
            fixFragmentManagerMemoryLeak(fragmentManager)

            adapter = adapterBuilder.getViewPagerAdapter(fragmentManager)
            currentItem = viewModel.position
        }
    }

    /*
    * Method fixes a strange memory leak. The fragments in fragment manager is not
    * releases after onDestroy and onDetach events. They still alive after rotation event
    * and only PostSamplesFragment removing fix the problem. This method removes fragments
    * manually from fragment holder using fragment manager. */
    private fun fixFragmentManagerMemoryLeak(fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        fragmentManager.fragments.forEach {
            transaction.remove(it)
        }
        transaction.commit()
        println(fragmentManager.fragments)
    }
}

