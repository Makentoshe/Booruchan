package com.makentoshe.booruchan.application.android.screen.samples

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.di.SampleContentScope
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_ERROR_CODE
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_ERROR_DATA
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_SUCCESS_CODE
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleContentFragmentViewModel
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sample_content.*
import toothpick.ktp.delegate.inject

/**
 * Fragment that should choose how to display a sample.
 *
 * There are three different types of samples: Image, Gif-animation and Webm-video.
 * This fragment should contain a nested fragment for displaying one of those types
 * */
class SampleContentFragment : CoreFragment() {

    companion object {
        fun build(booruContextClass: Class<BooruContext>, post: Post): SampleContentFragment {
            val fragment = SampleContentFragment()
            fragment.arguments.post = post
            fragment.arguments.booruclass = booruContextClass
            return fragment
        }
    }

    val arguments = Arguments(this)

    private val viewModel by inject<SampleContentFragmentViewModel>()
    private val disposables by inject<CompositeDisposable>(SampleContentScope::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.previewObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            fragment_sample_failure_preview.setImageBitmap(it)
        }.let(disposables::add)

        viewModel.exceptionObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            onContentLoadFailure(it)
        }.let(disposables::add)

        val fragment = when (arguments.post.sampleContent.type) {
            else -> SampleImageFragment.build(arguments.booruclass, arguments.post)
        }
        childFragmentManager.beginTransaction().add(R.id.fragment_sample_content, fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) = when (requestCode) {
        SAMPLE_CONTENT_SUCCESS_CODE -> onContentLoadSuccess()
        SAMPLE_CONTENT_ERROR_CODE -> onContentLoadFailure(data?.getSerializableExtra(SAMPLE_CONTENT_ERROR_DATA) as Throwable)
        else -> Unit // ignore
    }

    private fun onContentLoadSuccess() {
        println("success content")
    }

    private fun onContentLoadFailure(throwable: Throwable?) {
        fragment_sample_failure_preview.visibility = View.VISIBLE
        println("exception $throwable")
    }

    class Arguments(fragment: SampleContentFragment) : FragmentArguments<SampleContentFragment>(fragment) {

        var post: Post
            get() = fragmentArguments.getSerializable(POST) as Post
            set(value) = fragmentArguments.putSerializable(POST, value)

        var booruclass: Class<BooruContext>
            get() = fragmentArguments.getSerializable(CLASS) as Class<BooruContext>
            set(value) = fragmentArguments.putSerializable(CLASS, value)

        companion object {
            private const val POST = "post"
            private const val CLASS = "class"
        }
    }

}
