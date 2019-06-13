package com.makentoshe.boorupostview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.view.clicks
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.style.OnBackFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostsFragment : Fragment(), OnBackFragment {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar(view)
        setMagnifyIcon(view)
        attachPanelFragment()
        attachContentFragment()
    }

    private fun attachPanelFragment() {
        val containerId = com.makentoshe.boorupostview.R.id.panelview
        val f = childFragmentManager.findFragmentById(containerId)
        if (f != null) return
        val fragment = PostsPanelFragment.build(booru, tags)
        childFragmentManager.beginTransaction().add(containerId, fragment).commit()
    }

    private fun attachContentFragment() {
        val containerId = com.makentoshe.boorupostview.R.id.contentview
        val f = childFragmentManager.findFragmentById(containerId)
        if (f != null) return
        val fragment = PostsContentFragment.build(booru, tags)
        childFragmentManager.beginTransaction().add(containerId, fragment).commit()
    }

    private fun setToolbar(root: View) {
        //set toolbar titles
        val toolbar = root.findViewById<Toolbar>(com.makentoshe.boorupostview.R.id.toolbar_view)
        toolbar.title = booru.title
        toolbar.subtitle = getString(com.makentoshe.boorupostview.R.string.posts)
    }

    private fun setMagnifyIcon(root: View) {
        val magnifyIcon = root.findViewById<ImageView>(com.makentoshe.boorupostview.R.id.magnify_icon)
        val magnifyView = root.findViewById<View>(com.makentoshe.boorupostview.R.id.magnify_view)
        val slidingpanel = root.findViewById<SlidingUpPanelLayout>(com.makentoshe.boorupostview.R.id.slidingPanel)

        //set magnify icon drawable
        val magnifyDrawable = requireContext().getDrawable(com.makentoshe.style.R.drawable.avd_cross_magnify)
        magnifyIcon.setImageDrawable(magnifyDrawable)

        //on magnify click - collapse the panel
        magnifyView.clicks().subscribe {
            slidingpanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }.also { disposables.add(it) }

        //magnify depends on the panel slide offset
        slidingpanel.addPanelSlideListener(MagnifyPanelSlideListener(magnifyView))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun onBackPressed(): Boolean {
        val v = view?.findViewById<SlidingUpPanelLayout>(com.makentoshe.boorupostview.R.id.slidingPanel) ?: return false
        if (v.panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            v.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            return true
        }

        return false
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}
