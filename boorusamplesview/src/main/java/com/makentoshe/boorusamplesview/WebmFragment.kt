package com.makentoshe.boorusamplesview

import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorusamplesview.model.ExoPlayerWrapper
import com.makentoshe.boorusamplesview.view.WebmFragmentUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext

/**
 * Fragment for a webm video files. Loads and displays a webm from url
 */
class WebmFragment : Fragment() {

    /** Current [Booru] API instance used for requests */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Booru", value)
        get() = arguments!!.get("Booru") as Booru

    /** Current [Post] instance used for requests */
    private var post: Post
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Post", value)
        get() = arguments!!.get("Post") as Post

    /** Position of a container */
    private var position: Int
        set(value) = (arguments ?: Bundle().also { arguments = it }).putInt("Position", value)
        get() = arguments!!.getInt("Position")

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Player wrapper */
    private val player by lazy {
        ExoPlayerWrapper(ExoPlayerFactory.newSimpleInstance(requireContext()), requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return WebmFragmentUi().createView(AnkoContext.create(ContextWrapper(requireContext().applicationContext)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val playerview = view.findViewById<PlayerView>(com.makentoshe.boorusamplesview.R.id.playerview)
        player.source(post.sampleUrl).attachToView(playerview)
    }

    /** Release disposables, player */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        player.release()
    }

    companion object {
        /** Builds a [WebmFragment] instance */
        fun build(booru: Booru, post: Post, position: Int): Fragment {
            val fragment = WebmFragment()
            fragment.booru = booru
            fragment.post = post
            fragment.position = position
            return fragment
        }
    }
}
