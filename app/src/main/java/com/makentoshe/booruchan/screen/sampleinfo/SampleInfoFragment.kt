package com.makentoshe.booruchan.screen.sampleinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.repository.AsyncRepositoryAccess
import com.makentoshe.booruchan.repository.CachedRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.arguments
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.runOnUiThread
import java.io.Serializable

class SampleInfoFragment : Fragment() {

    private var itemPosition: Int
        get() = arguments!!.getInt(ITEM_ID)
        set(value) = arguments().putInt(ITEM_ID, value)

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val disposables = CompositeDisposable()

    private val asyncRepositoryAccess by lazy {
        val cache = PostInternalCache(requireContext())
        val source = PostsRepository(booru)
        val repository = CachedRepository(cache, source)
        AsyncRepositoryAccess(repository).apply {
            request(Booru.PostRequest(1, position, tags))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewpager = view.find<ViewPager>(R.id.sampleinfo_viewpager)
        val progressbar = view.find<View>(R.id.sampleinfo_progressbar)

        disposables.add(asyncRepositoryAccess.onComplete {
            runOnUiThread {
                progressbar.visibility = View.GONE
                viewpager.visibility = View.VISIBLE
                viewpager.adapter = SampleInfoViewPagerAdapter(childFragmentManager, booru, it[0])
                viewpager.currentItem = itemPosition
            }
        })

        disposables.add(asyncRepositoryAccess.onError {
            runOnUiThread {
                progressbar.visibility = View.GONE
                it.printStackTrace()
            }
        })

        val infotoolbar = view.find<View>(R.id.sampleinfo_toolbar_info)
        val tagstoolbar = view.find<View>(R.id.sampleinfo_toolbar_tags)
        val commtoolbar = view.find<View>(R.id.sampleinfo_toolbar_comments)

        val animator = SampleInfoUiToolbarAnimator(tagstoolbar, infotoolbar, commtoolbar)

        viewpager.onPageChangeListener {
            onPageScrolled { position, offset, _ ->
                animator.onPageScrolled(position, offset)
            }
        }
    }

    companion object {
        private const val ITEM_ID = "ItemId"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"
        fun create(itemId: Int, booru: Booru, tags: Set<Tag>, position: Int) = SampleInfoFragment().apply {
            this.booru = booru
            this.tags = tags
            this.position = position
            this.itemPosition = when (itemId) {
                R.id.bottombar_commentsitem -> 2
                R.id.bottombar_tagsitem -> 1
                R.id.bottombar_infoitem -> 0
                else -> 0
            }
        }
    }
}