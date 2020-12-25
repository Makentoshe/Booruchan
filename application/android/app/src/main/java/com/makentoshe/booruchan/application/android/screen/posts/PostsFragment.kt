package com.makentoshe.booruchan.application.android.screen.posts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.forEach
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.common.dp2px
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostAdapter
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostStateAdapter
import com.makentoshe.booruchan.application.android.screen.posts.view.CustomSpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsSlidingUpPanelListener
import com.makentoshe.booruchan.application.android.screen.posts.view.SpacesItemDecoration
import com.makentoshe.booruchan.application.android.screen.posts.view.SpannedGridLayoutManagerLookup
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import com.makentoshe.booruchan.application.android.screen.search.PostsSearchFragment
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.tagsFromText
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.ktor.client.features.*
import io.ktor.network.sockets.*
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import toothpick.ktp.delegate.inject
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

internal const val SEARCH_REQUEST_CODE = 1

internal const val SEARCH_REQUEST_EXTRA = "Search tags"

/** Fragment factory for [PostsFragment] allows to delivery arguments to the inner fragments */
class PostsFragmentFactory(private val booruclass: Class<BooruContext>) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) = when (className) {
        PostsSearchFragment::class.java.name -> PostsSearchFragment.build(booruclass)
        else -> super.instantiate(classLoader, className)
    }
}

class PostsFragment : CoreFragment() {

    companion object {
        fun build(booruclass: Class<BooruContext>): PostsFragment {
            val fragment = PostsFragment()
            fragment.arguments.booruclass = booruclass
            return fragment
        }
    }

    private val viewModel by inject<PostsFragmentViewModel>()
    private val adapter by inject<PostAdapter>()
    private val stateAdapter by inject<PostStateAdapter>()

    val arguments = Arguments(this)

    private val fragmentExceptionHandler by lazy { FragmentExceptionHandler(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.fragmentFactory = PostsFragmentFactory(arguments.booruclass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreatedSlidingPanel(view)
        onViewCreatedToolbar()
        onViewCreatedRecycler()

        fragment_posts_swipe.setOnRefreshListener { adapter.refresh() }
        fragment_posts_retry.setOnClickListener { adapter.retry() }
    }

    private fun onViewCreatedSlidingPanel(view: View) {
        fragment_posts_panel.isTouchEnabled = false
        fragment_posts_panel.addPanelSlideListener(PostsSlidingUpPanelListener(this, view))
    }

    private fun onViewCreatedToolbar() {
        fragment_posts_toolbar.title = arguments.booruclass.newInstance().title
        fragment_posts_toolbar.menu.forEach { item -> // change menu icons color to dimmed
            item.iconTintList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ColorStateList.valueOf(resources.getColor(R.color.dimmed, requireContext().theme))
            } else {
                ColorStateList.valueOf(resources.getColor(R.color.dimmed))
            }
        }
        fragment_posts_toolbar.setOnMenuItemClickListener(::onToolbarMenuClick)
    }

    private fun onToolbarMenuClick(item: MenuItem): Boolean {
        when (item.groupId) {
            R.id.posts_toolbar_search_open -> openSlidingPanel()
            R.id.posts_toolbar_search_close -> closeSlidingPanel()
        }
        return true
    }

    private fun closeSlidingPanel() {
        fragment_posts_panel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_open, true)
        fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_close, false)

        fragment_posts_toolbar.elevation = requireContext().dp2px(R.dimen.toolbar_elevation)
    }

    private fun openSlidingPanel() {
        fragment_posts_panel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_open, false)
        fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_close, true)

        fragment_posts_toolbar.elevation = 0f
    }

    private fun onViewCreatedRecycler() {
        val spannedGridLayoutManager = CustomSpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3)
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManagerLookup(adapter)
        fragment_posts_recycler.layoutManager = spannedGridLayoutManager
        fragment_posts_recycler.addItemDecoration(SpacesItemDecoration(8f))
        fragment_posts_recycler.adapter = adapter.withLoadStateFooter(stateAdapter)

        adapter.addLoadStateListener(::onLoadStateChanged)
        lifecycleScope.launch {
            viewModel.posts.collectLatest { adapter.submitData(it) }
        }
    }

    // TODO replace by custom handler class
    private fun onLoadStateChanged(states: CombinedLoadStates) {
        if (states.refresh is LoadState.Loading) {
            // is not refresh action (initial load)
            if (!states.prepend.endOfPaginationReached) {
                fragment_posts_progress.visibility = View.VISIBLE
                fragment_posts_recycler.isNestedScrollingEnabled = false
                fragment_posts_swipe.isEnabled = false
            }
            fragment_posts_retry.visibility = View.GONE
            fragment_posts_title.visibility = View.GONE
            fragment_posts_message.visibility = View.GONE
        }
        if (states.refresh is LoadState.NotLoading) {
            fragment_posts_recycler.isNestedScrollingEnabled = true
            fragment_posts_progress.visibility = View.GONE
            fragment_posts_swipe.visibility = View.VISIBLE
            fragment_posts_swipe.isRefreshing = false
            fragment_posts_swipe.isEnabled = true
        }
        if (states.refresh is LoadState.Error) {
            onInitialLoadFailure((states.refresh as LoadState.Error).error)
            fragment_posts_progress.visibility = View.GONE
            fragment_posts_swipe.isRefreshing = false
        }
    }

    private fun onInitialLoadFailure(exception: Throwable?) {
        val entry = fragmentExceptionHandler.handleException(exception)
        fragment_posts_swipe.visibility = View.GONE
        fragment_posts_title.text = entry.title
        fragment_posts_title.visibility = View.VISIBLE
        fragment_posts_message.text = entry.message
        fragment_posts_message.visibility = View.VISIBLE
        fragment_posts_retry.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) = when (requestCode) {
        SEARCH_REQUEST_CODE -> onActivityResultSearch(resultCode, data)
        else -> Unit
    }

    private fun onActivityResultSearch(resultCode: Int, data: Intent?) = if (resultCode == Activity.RESULT_OK) {
        onActivityResultSearchSuccess(data)
    } else {
        onActivityResultSearchFailure(data)
    }

    private fun onActivityResultSearchSuccess(data: Intent?) {
        if (data == null) return onActivityResultSearchFailure(data)
        val serializable = data.getSerializableExtra(SEARCH_REQUEST_EXTRA)
        val tags = tagsFromText((serializable as Array<Text>).toSet())

        closeSlidingPanel()

        lifecycleScope.launch {
            fragment_posts_recycler.scrollToPosition(0)
            fragment_posts_recycler.smoothScrollToPosition(0)
            fragment_posts_recycler.layoutManager?.scrollToPosition(0)
            viewModel.posts(tags).collectLatest { adapter.submitData(it) }
        }
    }

    private fun onActivityResultSearchFailure(data: Intent?) {
        Toast.makeText(requireContext(), "Search failure", Toast.LENGTH_LONG).show()
    }

    class Arguments(postsFragment: PostsFragment) : FragmentArguments(postsFragment) {

        var booruclass: Class<BooruContext>
            get() = fragmentArguments.getSerializable(CLASS) as Class<BooruContext>
            set(value) = fragmentArguments.putSerializable(CLASS, value)

        companion object {
            private const val CLASS = "class"
        }
    }

    class FragmentExceptionHandler(private val context: Context) {

        fun handleException(exception: Throwable?): Entry = when (exception) {
            is ArenaStorageException -> handleArenaStorageException(exception)
            else -> handleUnknownException(exception)
        }

        private fun handleUnknownException(exception: Throwable?): Entry {
            return Entry("There is an unknown error", exception?.message.toString())
        }

        private fun handleArenaStorageException(exception: ArenaStorageException): Entry =
            when (val cause = exception.cause) {
                // provider blocks the host
                is SSLPeerUnverifiedException -> {
                    val title = context.getString(R.string.exception_network)
                    Entry(title, cause.toString())
                }
                // internet connection disabled
                is UnknownHostException -> {
                    val title = context.getString(R.string.exception_network)
                    Entry(title, cause.toString())
                }

                is SSLHandshakeException -> {
                    val title = context.getString(R.string.exception_network)
                    Entry(title, cause.toString())
                }
                // Orbot connection expired cause
                is SocketTimeoutException -> {
                    val title = context.getString(R.string.exception_network)
                    val message = context.getString(R.string.exception_network_proxy)
                    Entry(title, message)
                }
                // TODO server requests to resolve captcha event
                is ClientRequestException -> {
                    val title = context.getString(R.string.exception_network)
                    val message = context.getString(R.string.exception_network_proxy_cloudflare)
                    Entry(title, message)
                }

                else -> {
                    exception.printStackTrace()
                    Entry("There is an unknown cache error", cause.toString())
                }
            }

        data class Entry(val title: String, val message: String, val image: Drawable? = null)
    }
}