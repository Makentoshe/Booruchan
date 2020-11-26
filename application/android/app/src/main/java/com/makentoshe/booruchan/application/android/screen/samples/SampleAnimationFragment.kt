package com.makentoshe.booruchan.application.android.screen.samples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.di.SampleAnimationScope
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_ERROR_CODE
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_ERROR_DATA
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleAnimationFragmentViewModel
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sample_animation.*
import toothpick.ktp.delegate.inject

/**
 * Fragment loads and displays gif animation.
 *
 * Should be placed to parent [SampleContentFragment] fragment
 */
class SampleAnimationFragment : CoreFragment() {

    companion object {
        fun build(booruContextClass: Class<BooruContext>, post: Post): SampleAnimationFragment {
            val fragment = SampleAnimationFragment()
            fragment.arguments.post = post
            fragment.arguments.booruclass = booruContextClass
            return fragment
        }
    }

    val arguments = Arguments(this)
    private val viewModel by inject<SampleAnimationFragmentViewModel>()
    private val disposables by inject<CompositeDisposable>(SampleAnimationScope::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.previewObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            fragment_sample_preview.visibility = View.VISIBLE
            fragment_sample_preview.setImageBitmap(it)
        }.let(disposables::add)

        // todo if sample content does not gif file - switch to full content automatically
        viewModel.sampleObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            fragment_sample_sample.visibility = View.VISIBLE
            fragment_sample_sample.setImageDrawable(it)
            fragment_sample_progress_indeterminate.visibility = View.GONE
            fragment_sample_preview.visibility = View.GONE
        }.let(disposables::add)

        viewModel.exceptionObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            // todo start loading full on GifIOException
            val intent = Intent().putExtra(SAMPLE_CONTENT_ERROR_DATA, it)
            parentFragment?.onActivityResult(SAMPLE_CONTENT_ERROR_CODE, Activity.RESULT_OK, intent)
        }.let(disposables::add)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    class Arguments(fragment: SampleAnimationFragment) : FragmentArguments<SampleAnimationFragment>(fragment) {

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