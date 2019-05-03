package com.makentoshe.booruchan.screen.samples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.SamplePageImageViewModel
import com.makentoshe.booruchan.screen.samples.controller.CircularProgressBarController
import com.makentoshe.booruchan.screen.samples.model.SampleOptionsMenu
import com.makentoshe.booruchan.screen.samples.controller.SamplePageImageController
import com.makentoshe.booruchan.screen.samples.view.SamplePageImageUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment downloads, contains and displays an image content.
 * Downloading performs in a viewmodel, so the fragment recreations will not interrupt
 * the downloading process.
 *
 * This fragment is a part of a samples screen and must have a [SamplePageFragment] as a parent fragment,
 * because it tries to get the parents views - for example, a progress bar.
 */
class SamplePageImageFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    /* Contains disposables for disposing on fragment finished to avoid memleaks */
    private val disposables by inject<CompositeDisposable>()
    /* Controller for changing progress from stream downloading */
    private val streamListener by inject<StreamDownloadController>()
    /* ViewModel contains content downloading */
    private val viewModel by viewModel<SamplePageImageViewModel> {
        parametersOf(booru, post, disposables, streamListener)
    }
    /* Controller for displaying a progress bar with current progress value */
    private val progressController by inject<CircularProgressBarController> {
        parametersOf(streamListener)
    }
    /* Controller for displaying content */
    private val controller by currentScope.inject<SamplePageImageController> {
        parametersOf(viewModel)
    }
    /* Controller for displaying options menu */
    private val optionsController by inject<SampleOptionsMenu> {
        parametersOf(booru, post)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageImageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pview = parentFragment!!.view!!
        //progress displaying controller
        progressController.bindView(pview)
        //content displaying controller
        controller.bindView(pview)
        //on long click to the view
        view.onLongClick {
            optionsController.show(this@SamplePageImageFragment)
        }
        //and on the preview
        pview.find<ImageView>(R.id.samples_preview).onLongClick {
            optionsController.show(this@SamplePageImageFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //dispose all subscribers to avoid memleaks.
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SamplePageImageFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}
