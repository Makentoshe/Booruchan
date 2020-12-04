package com.makentoshe.booruchan.application.android.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import com.google.android.exoplayer2.SimpleExoPlayer
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.di.SampleVideoScope
import com.makentoshe.booruchan.application.android.screen.samples.model.SampleVideoPlayerEventListener
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleVideoFragmentViewModel
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sample_video.*
import toothpick.ktp.delegate.inject

class SampleVideoFragment : CoreFragment() {

    companion object {
        fun build(booruclass: Class<BooruContext>, post: Post): SampleVideoFragment {
            val fragment = SampleVideoFragment()
            fragment.arguments.booruclass = booruclass
            fragment.arguments.post = post
            return fragment
        }
    }

    val arguments = Arguments(this)
    private val viewModel by inject<SampleVideoFragmentViewModel>()
    private val disposables by inject<CompositeDisposable>(SampleVideoScope::class)
    private val player by inject<SimpleExoPlayer>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_sample_video, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_sample_player.player = player
        player.addListener(SampleVideoPlayerEventListener(fragment_sample_progress_indeterminate))

        viewModel.previewObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            fragment_sample_player.defaultArtwork = it.toDrawable(resources)
        }.let(disposables::add)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.stop(true)
        disposables.clear()
    }

    class Arguments(fragment: SampleVideoFragment) : FragmentArguments<SampleVideoFragment>(fragment) {

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

