package com.makentoshe.boorupostview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.R
import com.makentoshe.boorupostview.presenter.PostsFragmentRxPresenterImpl
import com.makentoshe.boorupostview.view.PostsFragmentUi
import com.makentoshe.style.OnBackFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Contains sliding up/down panel layout and setups a main ui elements logic.
 */
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
        val presenter = PostsFragmentRxPresenterImpl(disposables, booru, tags, requireFragmentManager())

        val toolbar = view.findViewById<Toolbar>(com.makentoshe.boorupostview.R.id.toolbar_view)
        presenter.bindToolbar(toolbar)

        val optionIcon = view.findViewById<View>(R.id.magnify_view)
        presenter.bindOptionIcon(optionIcon)

        val slidingPanel = view.findViewById<SlidingUpPanelLayout>(com.makentoshe.boorupostview.R.id.slidingPanel)
        presenter.bindSlidingPanel(slidingPanel)
    }

    override fun onBackPressed(): Boolean {
        val v = view?.findViewById<SlidingUpPanelLayout>(R.id.slidingPanel) ?: return false
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
