package com.makentoshe.booruchan.screen.sampleinfo.controller

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.anko.onPageChangeListener
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadEventListener
import com.makentoshe.booruchan.screen.sampleinfo.model.SampleInfoViewPagerAdapter
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoUiToolbarAnimator
import org.jetbrains.anko.find

class SampleInfoViewController(
    private val root: View,
    private val downloadListener: PostsDownloadEventListener,
    private val booru: Booru,
    private val itemPosition: Int
) {

    private val progressbar by lazy { root.find<View>(R.id.samples_progress) }
    private val infotoolbar by lazy { root.find<View>(R.id.sampleinfo_toolbar_info) }
    private val tagstoolbar by lazy { root.find<View>(R.id.sampleinfo_toolbar_tags) }
    private val commtoolbar by lazy { root.find<View>(R.id.sampleinfo_toolbar_comments) }
    private val viewpager by lazy { root.find<ViewPager>(R.id.sampleinfo_viewpager) }
    private val animator by lazy {
        SampleInfoUiToolbarAnimator(tagstoolbar, infotoolbar, commtoolbar)
    }

    fun bind(fragment: Fragment) {
        //set toolbar animations for pages swipe
        viewpager.onPageChangeListener {
            onPageScrolled { position, offset, _ ->
                animator.onPageScrolled(position, offset)
            }
        }

        downloadListener.onSuccess { onSuccess(it[0], fragment.childFragmentManager) }

        downloadListener.onError { onError(it) }
    }

    private fun onSuccess(post: Post, fragmentManager: FragmentManager) {
        hideProgressBar()
        viewpager.visibility = View.VISIBLE
        viewpager.adapter =
            SampleInfoViewPagerAdapter(fragmentManager, booru, post)
        viewpager.currentItem = itemPosition
    }

    private fun onError(throwable: Throwable) {
        hideProgressBar()
        throwable.printStackTrace()
    }

    private fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

}