package com.makentoshe.booruchan.screen.samples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.SamplePageGifViewModel
import com.makentoshe.booruchan.screen.samples.controller.CircularProgressBarController
import com.makentoshe.booruchan.screen.samples.model.SampleOptionsMenu
import com.makentoshe.booruchan.screen.samples.controller.SamplePageGifController
import com.makentoshe.booruchan.screen.samples.view.SamplePageGifUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.droidsonroids.gif.GifImageView

class SamplePageGifFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    /* Container for disposables that must be disposed on fragment destroyed
     * to avoiding memory leaks.
     * */
    private val disposables by inject<CompositeDisposable>()
    /* Listener for streaming downloads */
    private val streamListener by inject<StreamDownloadController>()
    /* Controller for displaying a gif image after it will be downloaded */
    private val contentController by currentScope.inject<SamplePageGifController> {
        parametersOf(viewModel)
    }
    /* Controller for displaying progress in progress bar */
    private val progressBarController by inject<CircularProgressBarController> {
        parametersOf(streamListener)
    }
    /* Controller for displaying options menu */
    private val optionsController by inject<SampleOptionsMenu> {
        parametersOf(booru, post)
    }
    /* ViewModel holds the downloading to avoid downloading interrupts on fragment recreate */
    private val viewModel by viewModel<SamplePageGifViewModel> {
        parametersOf(booru, post, disposables, streamListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageGifUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pview = parentFragment!!.view!!
        //progress controller for displaying a progress
        progressBarController.bindView(pview)
        //content displaying controller
        contentController.bindView(pview)

        pview.find<GifImageView>(R.id.samples_gif).onLongClick {
            optionsController.show(this@SamplePageGifFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SamplePageGifFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}
