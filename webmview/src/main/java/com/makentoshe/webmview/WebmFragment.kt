package com.makentoshe.webmview

import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.ui.PlayerView
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext

class WebmFragment : Fragment() {

    /** Position of a container */
    private var position: Int
        set(value) = (arguments ?: Bundle().also { arguments = it }).putInt("Position", value)
        get() = arguments!!.getInt("Position")

    /** Position of a container */
    private var videopath: String
        set(value) = (arguments ?: Bundle().also { arguments = it }).putString("Address", value)
        get() = arguments!!.getString("Address") ?: ""

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Creates an user interface */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // creates anko context from application context to avoid style errors with exo player.
        return WebmFragmentUi().createView(AnkoContext.create(ContextWrapper(requireContext().applicationContext)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // create factory
        val factory = WebmFragmentViewModel.Factory(requireActivity().application) { player.source(videopath)}
        // create viewmodel
        val viewmodel = ViewModelProviders.of(this, factory)[WebmFragmentViewModel::class.java]

        val playerview = view.findViewById<PlayerView>(com.makentoshe.webmview.R.id.playerview)
        viewmodel.player.attachToView(playerview)
    }

    /** Release disposables, player */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        /** Builds a [WebmFragment] instance */
        fun build(videoaddress: String, position: Int): Fragment {
            val fragment = WebmFragment()
            fragment.videopath = videoaddress
            fragment.position = position
            return fragment
        }
    }
}
