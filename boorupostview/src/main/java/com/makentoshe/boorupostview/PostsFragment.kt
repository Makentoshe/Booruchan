package com.makentoshe.boorupostview

import android.content.Context
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
import com.makentoshe.style.materialButton
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.*
import java.io.Serializable

class PostsFragment : Fragment() {

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
        val fragment = PostsPanelFragment.build(booru, tags)
        childFragmentManager.beginTransaction().add(containerId, fragment).commit()
    }

    private fun attachContentFragment() {
        val containerId = com.makentoshe.boorupostview.R.id.contentview
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

class MagnifyPanelSlideListener(private val magnifyView: View) : SlidingUpPanelLayout.SimplePanelSlideListener() {
    /* Hide the magnifyView when panel is fully hide (offset == 0) */
    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        magnifyView.alpha = slideOffset
        magnifyView.visibility = if (slideOffset == 0f) View.GONE else View.VISIBLE
    }
}

class PostsPanelFragment : Fragment() {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsPanelFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsPanelFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}

class PostsContentFragment : Fragment() {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsContentFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsContentFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}

class PostsContentFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        val style = attr(com.makentoshe.style.R.attr.toolbar_style).data
        val matbutStyle = attr(com.makentoshe.style.R.attr.button_style_material).data
        themedRelativeLayout(style) {
            lparams(matchParent, matchParent)

            materialButton(matbutStyle) {
                text = "SAS"
            }.lparams(matchParent, dip(56)) {
                alignParentBottom()
            }
        }
    }
}

class PostsPanelFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        relativeLayout {
            textView("IMAGES HERE")
        }
    }

}
