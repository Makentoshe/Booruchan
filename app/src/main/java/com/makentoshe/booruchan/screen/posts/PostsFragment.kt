package com.makentoshe.booruchan.screen.posts

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
import com.makentoshe.booruchan.screen.posts.controller.BottomBarController
import com.makentoshe.booruchan.screen.posts.controller.MagnifyController
import com.makentoshe.booruchan.screen.posts.controller.ToolbarController
import com.makentoshe.booruchan.screen.posts.controller.ViewPagerController
import com.makentoshe.booruchan.screen.posts.model.PostsViewPagerAdapter
import com.makentoshe.booruchan.screen.posts.view.PostsUi
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchState
import com.makentoshe.booruchan.screen.posts.viewmodel.SearchStateViewModel
import com.makentoshe.booruchan.screen.posts.viewmodel.TagsViewModel
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.io.Serializable

class PostsFragment : Fragment(), BooruHolder {

    init {
        currentScope.get<Fragment>(named(PostsModule.fragmentStr)) { parametersOf(this) }
        currentScope.get<BooruHolder>(named(PostsModule.booruStr)) { parametersOf(this) }
    }

    override var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val toolbarController by currentScope.inject<ToolbarController>()

    private val tagsViewModel by viewModel<TagsViewModel> { parametersOf(tags) }

    private val searchStateViewModel by viewModel<SearchStateViewModel> {
        parametersOf(tagsViewModel, tags)
    }

    private val bottomBarController by currentScope.inject<BottomBarController> {
        parametersOf(searchStateViewModel)
    }

    private val viewPagerController by currentScope.inject<ViewPagerController> {
        parametersOf(searchStateViewModel)
    }

    private val magnifyController by currentScope.inject<MagnifyController> {
        parametersOf(tagsViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentScope.get<SearchState> { parametersOf(searchStateViewModel) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerController.bindView(view)
        magnifyController.bindView(view)
        toolbarController.bindView(view)
        bottomBarController.bindView(view)
    }

//    private fun setDrawerMenuControl(drawer: DrawerLayout?, drawerIcon: View) {
//        if (drawer == null) return
//        drawerIcon.setOnClickListener {
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START)
//            } else {
//                drawer.openDrawer(GravityCompat.START)
//            }
//        }
//    }

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
        searchStateViewModel.startSearch(tags)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchStateViewModel.clearDisposables()
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
