package com.makentoshe.booruchan.application.android.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleImageFragmentViewModel
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import toothpick.ktp.delegate.inject

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
    private val disposables by inject<CompositeDisposable>(toString())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply { text = arguments.post.toString() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.sampleObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            println("sample success $it")
        }.let(disposables::add)

        viewModel.exceptionObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            println("sample error $it")
        }.let(disposables::add)
    }

    class Arguments(fragment: SampleImageFragment) : FragmentArguments<SampleImageFragment>(fragment) {

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

