package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.samples.view.SamplePageWebmUi
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.webmplayer.WebmPlayerScreen
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.koin.android.ext.android.inject

class SamplePageWebmFragment : Fragment() {

    private val router: Router by inject()

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageWebmUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pview = parentFragment!!.view!!
        if (AppSettings.getWebmPlayingOnPlace(requireContext())) {
            childFragmentManager.beginTransaction()
                .add(R.id.samples_webm_container, WebmPlayerScreen(booru, post).fragment)
                .commit()
            pview.find<View>(R.id.samples_progress).visibility = View.GONE
            pview.find<ImageView>(R.id.samples_preview).imageAlpha = 0
        } else {
            pview.find<View>(R.id.samples_progress).visibility = View.GONE
            val messageview = pview.find<TextView>(R.id.samples_message)
            messageview.setText(R.string.tap_to_play)
            messageview.visibility = View.VISIBLE
            messageview.bringToFront()
            pview.setOnClickListener {
                router.navigateTo(WebmPlayerScreen(booru, post))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val POST = "Post"
        private const val BOORU = "Booru"
        private const val POSITION = "Position"
        fun create(booru: Booru, post: Post, position: Int) = SamplePageWebmFragment().apply {
            this.booru = booru
            this.post = post
            this.position = position
        }
    }
}