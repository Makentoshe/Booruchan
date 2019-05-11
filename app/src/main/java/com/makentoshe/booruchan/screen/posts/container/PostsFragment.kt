package com.makentoshe.booruchan.screen.posts.container

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.posts.container.controller.*
import com.makentoshe.booruchan.screen.posts.container.model.PostsViewPagerAdapter
import com.makentoshe.booruchan.screen.posts.container.view.PostsUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.Serializable

class PostsFragment : Fragment(), BooruHolder {

    override var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val disposables by inject<CompositeDisposable>()

    private val toolbarController by inject<PostsToolbarController> {
        parametersOf(booru)
    }

    private val magnifyController by inject<PostsMagnifyController> {
        parametersOf(booru, tags, this)
    }

    private val bottomBarController by inject<PostsBottomBarController> {
        parametersOf(searchController)
    }

    private val viewPagerController by inject<PostsViewPagerController> {
        parametersOf(booru, searchController, childFragmentManager)
    }

    private val searchController: SearchController by viewModel<PostsViewModel> {
        parametersOf(tags, disposables)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarController.bindView(view)
        magnifyController.bindView(view)
        bottomBarController.bindView(view)
        viewPagerController.bindView(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        //if new search was started
        if (requestCode == RequestCode.search) onSearchResultReceived(data)
        //if child fragment does not received posts
        //it means that the posts is run out
        //the resultCode contains page without posts
        if (requestCode == RequestCode.postpage) {
            val pager = view!!.find<ViewPager>(R.id.posts_viewpager)
            val adapter = pager.adapter as PostsViewPagerAdapter
            pager.adapter = adapter.copy(count = resultCode)
            pager.currentItem = resultCode - 1
        }
    }

    private fun onSearchResultReceived(data: Intent) {
        val set = data.getSerializableExtra(Set::class.java.simpleName) as Set<*>
        val tags = set.filter { it is Tag }.map { it as Tag }.toSet()
        searchController.startSearch(tags)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(booru: Booru, tags: Set<Tag>) = PostsFragment().apply {
            this.booru = booru
            this.tags = tags
        }
    }
}
