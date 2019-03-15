package com.makentoshe.booruchan.screen.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.appSettings
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCacheType
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.RequestCode
import com.makentoshe.booruchan.screen.SubjectHolder
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.booru.inflator.BooruToolbarUiInflater
import com.makentoshe.booruchan.screen.posts.inflator.PostsUiBottomBarInflator
import com.makentoshe.booruchan.screen.posts.inflator.PostsUiToolbarInflator
import com.makentoshe.booruchan.screen.posts.model.PostsViewPagerAdapter
import com.makentoshe.booruchan.screen.posts.model.TagsHolder
import com.makentoshe.booruchan.screen.posts.view.PostsUi
import com.makentoshe.booruchan.screen.search.SearchDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class PostsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private val searchController by lazy {
        val subject = BehaviorSubject.create<Set<Tag>>()
        val holder = SubjectHolder.create(this, subject, "Search")
        return@lazy holder.subject
    }

    private val tagsHolder by lazy {
        TagsHolder.create(this)
    }

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentDoOnCreateOnce.create(this) {
            startNewSearch(setOf())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parentFragment?.view?.findViewById<DrawerLayout>(R.id.booru_drawer)?.let {
            BooruToolbarUiInflater(it).accept(view)
        }

        view.find<View>(R.id.posts_toolbar_search).onClick { showSearchFragment() }

        view.find<ViewPager>(R.id.posts_viewpager).let { v ->
            val disposable = searchController.subscribe { tags ->
                v.adapter = PostsViewPagerAdapter(childFragmentManager, tags, booru)
            }
            disposables.add(disposable)
        }

        PostsUiToolbarInflator(booru).accept(view)
        PostsUiBottomBarInflator().accept(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        //if new search was started
        if (requestCode == RequestCode.search) {
            //get tags
            val tags = data.getSerializableExtra(Set::class.java.simpleName) as Set<Tag>
            startNewSearch(tags)
        }
    }

    private fun startNewSearch(tags: Set<Tag>) {
        //clear caches
        GlobalScope.launch {
            PostInternalCache(requireContext()).clear()
            ImageInternalCache(requireContext(), InternalCacheType.SAMPLE).clear()
        }
        //set tags to holder
        tagsHolder.set.clear()
        tagsHolder.set.addAll(tags)
        //put here the default tags from settings
        val fixedTags = HashSet<Tag>()
        fixedTags.addAll(tags)
        //default tags for disable nsfw content
        if (!appSettings.getNSFW(requireContext())) fixedTags.add(Tag.create("rating:safe"))
        //notify
        searchController.onNext(fixedTags)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    private fun showSearchFragment() {
        val fragment = SearchDialogFragment.create(tagsHolder.set, booru)
        fragment.setTargetFragment(this, RequestCode.search)
        fragment.show(fragmentManager, SearchDialogFragment::class.java.simpleName)
    }

    companion object {
        private const val BOORU = "Booru"

        fun create(booru: Booru) = PostsFragment().apply {
            this.booru = booru
        }
    }
}