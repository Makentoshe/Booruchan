package com.makentoshe.booruchan.application.android.screen.samples

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import kotlinx.android.synthetic.main.fragment_sample_video.*

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

    private lateinit var player: ExoPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        player = ExoPlayerFactory.newSimpleInstance(requireContext())
        player.repeatMode = Player.REPEAT_MODE_ALL
        fragment_sample_player.player = player

        val uri = Uri.parse(arguments.post.sampleContent.url)
        val useragent = Util.getUserAgent(requireContext(), requireContext().getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), useragent)
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        player.prepare(mediaSource)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.stop(true)
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
