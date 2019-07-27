package com.makentoshe.imageview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.imageview.download.SampleItemContextMenuBuilder
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext

/**
 *  Fragments for an image files. Loads and displays an image as a byte array from post instance.
 */
class ImageFragment : Fragment() {

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
        return ImageFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menubuilder =
            SampleItemContextMenuBuilder(childFragmentManager, booru, post)
        val presenter = ImageFragmentPresenter(disposables, createViewModel(), menubuilder)
        // bind image view
        val imageview = view.findViewById<SubsamplingScaleImageView>(com.makentoshe.imageview.R.id.imageview)
        presenter.bindImageView(imageview)
        // bind indeterminate progress bar
        val indeterminateProgressBar = view.findViewById<ProgressBar>(com.makentoshe.style.R.id.indeterminateprogress)
        presenter.bindIndeterminateProgressBar(indeterminateProgressBar)
        // bind circular progress bar
        val circularProgressBar = view.findViewById<CircularProgressBar>(com.makentoshe.style.R.id.circularprogress)
        presenter.bindCircularProgressBar(circularProgressBar)
    }

    /** Creates a viewmodel instance */
    private fun createViewModel(): ImageFragmentViewModel {
        val repositoryBuilder = RepositoryBuilder(booru)
        val cacheBuilder = CacheBuilder(requireContext())
        val imageDecoder = AndroidImageDecoder()
        val factory = ImageFragmentViewModel.Factory(repositoryBuilder, cacheBuilder, imageDecoder) { viewmodel ->
            viewmodel.execute(post)
        }
        return ViewModelProviders.of(this, factory)[ImageFragmentViewModel::class.java]
    }

    /** Releases a disposables */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        /** Builds an [ImageFragment] instance */
        fun build(booru: Booru, post: Post): ImageFragment {
            val fragment = ImageFragment()
            fragment.booru = booru
            fragment.post = post
            return fragment
        }
    }
}
