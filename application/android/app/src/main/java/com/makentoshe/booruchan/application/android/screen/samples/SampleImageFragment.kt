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
import com.makentoshe.booruchan.application.android.screen.samples.di.SampleImageScope
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_ERROR_CODE
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_ERROR_DATA
import com.makentoshe.booruchan.application.android.screen.samples.model.SAMPLE_CONTENT_SUCCESS_CODE
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleImageFragmentViewModel
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sample_image.*
import toothpick.ktp.delegate.inject

/**
 * Fragment loads and displays image.
 *
 * Should be placed to parent [SampleContentFragment] fragment
 */
class SampleImageFragment : CoreFragment() {

    companion object {
        fun build(booruContextClass: Class<BooruContext>, post: Post): SampleImageFragment {
            val fragment = SampleImageFragment()
            fragment.arguments.post = post
            fragment.arguments.booruclass = booruContextClass
            return fragment
        }
    }

    val arguments = Arguments(this)
    private val viewModel by inject<SampleImageFragmentViewModel>()
    private val disposables by inject<CompositeDisposable>(SampleImageScope::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.previewObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            fragment_sample_preview.visibility = View.VISIBLE
            fragment_sample_preview.setImageBitmap(it)
        }.let(disposables::add)

        viewModel.sampleObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            fragment_sample_progress_indeterminate.visibility = View.GONE
            fragment_sample_sample.visibility = View.VISIBLE
            fragment_sample_sample.setImageBitmap(it)
            // notify parent fragment on success result
            parentFragment?.onActivityResult(SAMPLE_CONTENT_SUCCESS_CODE, Activity.RESULT_OK, null)
        }.let(disposables::add)

        viewModel.exceptionObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            // notify parent fragment on error result
            val intent = Intent().putExtra(SAMPLE_CONTENT_ERROR_DATA, it)
            parentFragment?.onActivityResult(SAMPLE_CONTENT_ERROR_CODE, Activity.RESULT_OK, intent)
        }.let(disposables::add)
//
//        fragment_sample_sample.setOnLongClickListener {
//            val fragment = ImageDialogFragment()
//            fragment.setTargetFragment(this, 0)
//            fragment.show(requireFragmentManager(), ImageDialogFragment::class.java.simpleName)
//            return@setOnLongClickListener true
//        }
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when (requestCode) {
//            REQUEST_CODE_SAVE_IMAGE -> downloadExecutor.downloadPostFullContent(arguments.post)
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    class Arguments(fragment: SampleImageFragment) : FragmentArguments(fragment) {

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
