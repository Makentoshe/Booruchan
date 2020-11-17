package com.makentoshe.booruchan.application.android.screen.posts.view

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
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import com.makentoshe.booruchan.application.android.screen.search.PostsSearchFragment
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import com.makentoshe.booruchan.core.Text
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_posts.*
import toothpick.ktp.delegate.inject
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

internal const val SEARCH_REQUEST_CODE = 1

internal const val SEARCH_REQUEST_EXTRA = "Search tags"

/** Fragment factory for [PostsFragment] allows to delivery arguments to the inner fragments */
class PostsFragmentFactory(private val booruContextTitle: String) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) = when (className) {
        PostsSearchFragment::class.java.name -> PostsSearchFragment.build(booruContextTitle)
        else -> super.instantiate(classLoader, className)
    }
}

class PostsFragment : CoreFragment() {

    companion object {
        fun build(booruContextTitle: String): PostsFragment {
            val fragment = PostsFragment()
            fragment.arguments.booruContextTitle = booruContextTitle
            return fragment
        }
    }

    private val viewModel by inject<PostsFragmentViewModel>()
    private val disposables by inject<CompositeDisposable>()
    val arguments = Arguments(this)

    private val fragmentExceptionHandler by lazy { FragmentExceptionHandler(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.fragmentFactory = PostsFragmentFactory(arguments.booruContextTitle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_posts_panel.isTouchEnabled = false

        onViewCreatedToolbar(view, savedInstanceState)
        onViewCreatedRecycler(view, savedInstanceState)

        viewModel.initialSignal.observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onInitialLoad).let(disposables::add)

        fragment_posts_retry.setOnClickListener { onInitialLoadRetry() }

        fragment_posts_panel.addPanelSlideListener(PostsSlidingUpPanelListener(this, view))
    }

    private fun onViewCreatedToolbar(view: View, savedInstanceState: Bundle?) {
        fragment_posts_toolbar.title = arguments.booruContextTitle
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
            R.id.posts_toolbar_search_open -> {
                fragment_posts_panel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_open, false)
                fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_close, true)
            }
            R.id.posts_toolbar_search_close -> {
                fragment_posts_panel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_open, true)
                fragment_posts_toolbar.menu.setGroupVisible(R.id.posts_toolbar_search_close, false)
            }
        }
        return true
    }

    private fun onViewCreatedRecycler(view: View, savedInstanceState: Bundle?) {
        val spannedGridLayoutManager = SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3)
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManagerLookup(viewModel.postsAdapter)
        fragment_posts_recycler.layoutManager = spannedGridLayoutManager
        fragment_posts_recycler.addItemDecoration(SpacesItemDecoration(8f))
        fragment_posts_recycler.adapter = viewModel.postsAdapter
    }

    private fun onInitialLoadRetry() {
        viewModel.retryLoadInitial()

        fragment_posts_progress.visibility = View.VISIBLE
        fragment_posts_retry.visibility = View.GONE
        fragment_posts_title.visibility = View.GONE
        fragment_posts_message.visibility = View.GONE
    }

    /** On [viewModel.inisialSignal] receive in ui thread */
    private fun onInitialLoad(result: Result<*>) {
        fragment_posts_progress.visibility = View.GONE
        if (result.isSuccess) {
            fragment_posts_recycler.visibility = View.VISIBLE
        } else {
            onInitialLoadFailure(result.exceptionOrNull())
        }
    }

    private fun onInitialLoadFailure(exception: Throwable?) {
        val entry = fragmentExceptionHandler.handleException(exception)
        fragment_posts_title.text = entry.title
        fragment_posts_title.visibility = View.VISIBLE
        fragment_posts_message.text = entry.message
        fragment_posts_message.visibility = View.VISIBLE
        fragment_posts_retry.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
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
        val tags = serializable as Array<Text>
        // TODO pass tags to the query
        println(tags.map { it.text })
    }

    private fun onActivityResultSearchFailure(data: Intent?) {
        Toast.makeText(requireContext(), "Search failure", Toast.LENGTH_LONG).show()
    }

    class Arguments(postsFragment: PostsFragment) : FragmentArguments<PostsFragment>(postsFragment) {

        var booruContextTitle: String
            get() = fragmentArguments.getString(TITLE)!!
            set(value) = fragmentArguments.putString(TITLE, value)

        companion object {
            private const val TITLE = "BooruContext#title"
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
                is SSLPeerUnverifiedException -> Entry("There is a network error", cause.toString())
                // internet connection disabled
                is UnknownHostException -> Entry("There is a network error", cause.toString())

                is SSLHandshakeException -> Entry("There is a network error", cause.toString())

                else -> Entry("There is an unknown cache error", exception.toString())
            }

        data class Entry(val title: String, val message: String, val image: Drawable? = null)
    }
}