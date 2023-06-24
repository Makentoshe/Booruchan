package com.makentoshe.booruchan.application.android.screen.samples

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.di.SampleVideoScope
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

    private val exceptionHandler = ExceptionHandler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_sample_video, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_sample_player.player = player
        player.addListener(object : Player.EventListener {

            override fun onPlayerError(error: ExoPlaybackException) = this@SampleVideoFragment.onPlayerError(error)

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) =
                this@SampleVideoFragment.onPlayerStateChanged(playbackState)
        })

        viewModel.previewObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            fragment_sample_player.defaultArtwork = it.toDrawable(resources)
        }.let(disposables::add)
    }

    private fun onPlayerStateChanged(playbackState: Int) {
        fragment_sample_progress_indeterminate.visibility = when (playbackState) {
            Player.STATE_BUFFERING -> View.VISIBLE
            Player.STATE_READY -> View.GONE
            else -> return
        }
    }

    private fun onPlayerError(exception: ExoPlaybackException?) {
        val entry = exceptionHandler.handleException(exception)
        Toast.makeText(requireContext(), entry.toString(), Toast.LENGTH_LONG).show()
        println(entry)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.stop(true)
        disposables.clear()
    }

    private class ExceptionHandler {

        fun handleException(exception: Throwable?): Entry = when (exception) {
            else -> handleUnknownException(exception)
        }

        private fun handleUnknownException(exception: Throwable?): Entry {
            return Entry("There is an unknown error", exception?.message.toString())
        }

        data class Entry(val title: String, val message: String, val image: Drawable? = null)
    }
    class Arguments(fragment: SampleVideoFragment) : FragmentArguments(fragment) {

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

