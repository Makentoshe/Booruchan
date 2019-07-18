package com.makentoshe.boorusamplesview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorusamplesview.presenter.GifFragmentPresenter
import com.makentoshe.boorusamplesview.view.GifFragmentUi
import com.makentoshe.boorusamplesview.viewmodel.GifFragmentViewModel
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import pl.droidsonroids.gif.GifImageView

/**
 * Fragment for gif files. Loads and plays a gif animation as a byte array from post instance.
 */
class GifFragment : Fragment() {

    /** Current [Booru] API instance used for requests */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Booru", value)
        get() = arguments!!.get("Booru") as Booru

    /** Current [Post] instance used for requests */
    private var post: Post
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Post", value)
        get() = arguments!!.get("Post") as Post

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return GifFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // create a presenter
        val presenter = GifFragmentPresenter(disposables, createViewModel())
        // bind gif image view
        val gifImageView = view.findViewById<GifImageView>(com.makentoshe.boorusamplesview.R.id.gifview)
        presenter.bindGifImageView(gifImageView)
        // bind indeterminate progress bar
        val indeterminateProgressBar = view.findViewById<ProgressBar>(
            com.makentoshe.boorusamplesview.R.id.indeterminateprogress
        )
        presenter.bindIndeterminateProgressBar(indeterminateProgressBar)
        // bind circular progress bar
        val circularProgressBar = view.findViewById<CircularProgressBar>(
            com.makentoshe.boorusamplesview.R.id.circularprogress
        )
        presenter.bindCircularProgressBar(circularProgressBar)
    }

    /** Returns a [GifFragmentViewModel] instance */
    private fun createViewModel(): GifFragmentViewModel {
        val repositoryBuilder = RepositoryBuilder(booru)
        val cacheBuilder = CacheBuilder(requireContext())
        val factory = GifFragmentViewModel.Factory(repositoryBuilder, cacheBuilder) { it.execute(post) }
        return ViewModelProviders.of(this, factory)[GifFragmentViewModel::class.java]
    }

    /** Release disposables */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        /** Builds an [GifFragment] instance */
        fun build(booru: Booru, post: Post): GifFragment {
            val fragment = GifFragment()
            fragment.booru = booru
            fragment.post = post
            return fragment
        }
    }
}
