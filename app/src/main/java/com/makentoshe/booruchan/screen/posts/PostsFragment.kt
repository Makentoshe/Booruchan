package com.makentoshe.booruchan.screen.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.model.SubjectHolder
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
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
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.subtitleResource
import org.jetbrains.anko.support.v4.onPageChangeListener

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
        val viewpager = view.find<ViewPager>(R.id.posts_viewpager)
        val toolbar = view.find<Toolbar>(R.id.booru_toolbar)
        val drawer = parentFragment?.view?.findOptional<DrawerLayout>(R.id.booru_drawer)
        val drawerIcon = view.find<View>(R.id.booru_toolbar_drawermenu)
        val search = view.find<View>(R.id.posts_toolbar_search)
        //set up the horizontal view pager for displaying the pages with the images
        //on each starting search the old adapter will be replaced by new one with
        //tags for current search
        setViewPagerAdapterControl(viewpager)
        //set up toolbar title and content subtitle
        setToolbarInfo(toolbar)
        //click on drawer menu
        setDrawerMenuControl(drawer, drawerIcon)
        //click on search icon in the toolbar
        search.onClick { showSearchFragment() }
        //set click controls for bottom bar
        bottomBarControl(viewpager, view)
    }

    private fun bottomBarControl(viewpager: ViewPager, root: View) {
        val bottomleft = root.find<View>(R.id.posts_bottombar_left)
        val bottomcenter = root.find<View>(R.id.posts_bottombar_center)
        val bottomright = root.find<View>(R.id.posts_bottombar_right)
        val centertext = bottomcenter.find<TextView>(R.id.posts_bottombar_center_textview)
        //if click was performed on first element
        if (viewpager.currentItem == 0) {
            bottomleft.visibility = View.INVISIBLE
        }
        //set default value text view in center
        centertext.text = "0"
        //set on right icon click listener
        bottomright.setOnClickListener {
            val currItem = viewpager.currentItem
            viewpager.setCurrentItem(currItem + 1, true)
        }

        viewpager.onPageChangeListener {
            onPageSelected {
                //change text when page was scrolled
                centertext.text = it.toString()
                //when scrolled to the first page - hide the left chevron
                //else display and setup functional
                if (it < 1) {
                    bottomleft.visibility = View.INVISIBLE
                    bottomleft.setOnClickListener(null)
                } else {
                    bottomleft.visibility = View.VISIBLE
                    bottomleft.setOnClickListener {
                        //on left chevron click
                        val currItem = viewpager.currentItem
                        viewpager.setCurrentItem(currItem - 1, true)
                    }
                }
            }
        }
    }

    private fun setToolbarInfo(toolbar: Toolbar) {
        toolbar.title = booru.title
        toolbar.subtitleResource = R.string.posts
    }

    private fun setViewPagerAdapterControl(viewpager: ViewPager) {
        val disposable = searchController.subscribe { tags ->
            viewpager.adapter = PostsViewPagerAdapter(childFragmentManager, tags, booru)
        }
        disposables.add(disposable)
    }

    private fun setDrawerMenuControl(drawer: DrawerLayout?, drawerIcon: View) {
        if (drawer == null) return
        drawerIcon.setOnClickListener {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun startNewSearch(tags: Set<Tag>) {
        //clear caches
        GlobalScope.launch {
            PostInternalCache(requireContext()).clear()
            ImageInternalCache(requireContext(), InternalCache.Type.SAMPLE).clear()
            ImageInternalCache(requireContext(), InternalCache.Type.PREVIEW).clear()
            ImageInternalCache(requireContext(), InternalCache.Type.FILE).clear()
        }
        //set tags to holder
        tagsHolder.set.clear()
        tagsHolder.set.addAll(tags)
        //notify
        searchController.onNext(tags)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        //if new search was started
        if (requestCode == RequestCode.search) {
            //get tags
            val tags = data.getSerializableExtra(Set::class.java.simpleName) as Set<Tag>
            startNewSearch(tags)
        }
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