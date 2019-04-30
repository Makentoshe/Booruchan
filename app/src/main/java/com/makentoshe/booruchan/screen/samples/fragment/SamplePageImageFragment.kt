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
import com.makentoshe.booruchan.screen.samples.controller.ProgressBarController
import com.makentoshe.booruchan.screen.samples.controller.SampleOptionsController
import com.makentoshe.booruchan.screen.samples.controller.SamplePageImageController
import com.makentoshe.booruchan.screen.samples.view.SamplePageImageUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SamplePageImageFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val disposables by inject<CompositeDisposable>()

    private val streamListener by inject<StreamDownloadController>()

    private val viewModel by viewModel<SamplePageImageViewModel> {
        parametersOf(booru, post, disposables, streamListener)
    }

    private val progressController by lazy {
        ProgressBarController(streamListener)
    }

    private val controller by lazy {
        SamplePageImageController(viewModel)
    }

    private val optionsController by lazy {
        SampleOptionsController(booru, post)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageImageUi().createView(AnkoContext.create(requireContext(), this))
    }

    /* Starts loading image file */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pview = parentFragment!!.view!!

        progressController.bindView(pview)

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
