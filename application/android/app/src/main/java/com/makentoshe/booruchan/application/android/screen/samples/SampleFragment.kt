package com.makentoshe.booruchan.application.android.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleFragmentViewModel
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sample.*
import toothpick.ktp.delegate.inject

class SampleFragment : CoreFragment() {

    companion object {
        fun build(booruContextClass: Class<BooruContext>, post: Post): SampleFragment {
            val fragment = SampleFragment()
            fragment.arguments.post = post
            fragment.arguments.booruclass = booruContextClass
            return fragment
        }
    }

    val arguments = Arguments(this)

    private val viewModel by inject<SampleFragmentViewModel>()
    private val disposables by inject<CompositeDisposable>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_sample_message.text = arguments.post.toString()

        viewModel.previewObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            println("preview $it")
        }.let(disposables::add)

        viewModel.exceptionObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            println("exception $it")
        }.let(disposables::add)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    class Arguments(fragment: SampleFragment) : FragmentArguments<SampleFragment>(fragment) {

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
