package com.makentoshe.booruchan.application.android.screen.posts.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_posts.*
import toothpick.ktp.delegate.inject
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

class PostsFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_posts_toolbar.title = arguments.booruContextTitle
        fragment_posts_toolbar.menu.forEach { item ->
            item.iconTintList = ColorStateList.valueOf(resources.getColor(R.color.dimmed, requireContext().theme))
        }
        fragment_posts_toolbar.setOnMenuItemClickListener {
            Toast.makeText(requireContext(), "Search", Toast.LENGTH_LONG).show()
            return@setOnMenuItemClickListener true
        }

        val spannedGridLayoutManager = SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3)
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManagerLookup(viewModel.postsAdapter)
        fragment_posts_recycler.layoutManager = spannedGridLayoutManager
        fragment_posts_recycler.addItemDecoration(SpacesItemDecoration(8f))
        fragment_posts_recycler.adapter = viewModel.postsAdapter

        viewModel.initialSignal.observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onInitialLoad).let(disposables::add)

        fragment_posts_retry.setOnClickListener { onInitialLoadRetry() }
    }

    private fun onInitialLoadRetry() {
        viewModel.retryLoadInitial()

        fragment_posts_progress.visibility = View.VISIBLE
        fragment_posts_retry.visibility = View.GONE
        fragment_posts_title.visibility = View.GONE
        fragment_posts_message.visibility = View.GONE
    }

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

    class Arguments(private val postsFragment: PostsFragment) {

        init {
            val fragment = postsFragment as Fragment
            if (fragment.arguments == null) {
                fragment.arguments = Bundle()
            }
        }

        private val fragmentArguments: Bundle
            get() = postsFragment.requireArguments()

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

                else -> Entry("There is an unknown cache error", exception.toString())
            }

        data class Entry(val title: String, val message: String, val image: Drawable? = null)
    }
}