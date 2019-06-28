package com.makentoshe.boorupostview.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.NewSearchBroadcastReceiver
import com.makentoshe.boorupostview.PostSelectBroadcastReceiver
import com.makentoshe.boorupostview.PostsFragmentNavigator
import com.makentoshe.boorupostview.R
import com.makentoshe.boorupostview.model.ItemsCountCalculator
import com.makentoshe.boorupostview.presenter.PostsFragmentRxPresenter
import com.makentoshe.boorupostview.view.PostsFragmentUi
import com.makentoshe.boorupostview.viewmodel.PostsFragmentViewModel
import com.makentoshe.style.OnBackFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.subtitleResource
import java.io.Serializable

/**
 * Contains sliding up/down panel layout and setups a main ui elements logic.
 */
class PostsFragment : Fragment(), OnBackFragment {

    /** Broadcast receiver for receiving a new search events from another fragment */
    private val newSearchBroadcastReceiver = NewSearchBroadcastReceiver()

    /** Broadcast receiver handle post select events */
    private val postSelectBroadcastReceiver = PostSelectBroadcastReceiver()

    /** Contains a disposable which will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Booru API instance */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    /** Current set of the tags to search */
    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Navigator to another screens */
    private var navigator: PostsFragmentNavigator
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(NAVIGATOR, value)
        get() = arguments!!.get(NAVIGATOR) as PostsFragmentNavigator

    /** Viewmodel component performs cache controlling */
    private lateinit var viewmodel: PostsFragmentViewModel

    /** Register broadcast receivers */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // calculate a total items count per page
        val calculator = ItemsCountCalculator()
        // register a new search event
        NewSearchBroadcastReceiver.registerReceiver(requireActivity(), newSearchBroadcastReceiver)
        // register a select event
        PostSelectBroadcastReceiver.registerReceiver(requireActivity(), postSelectBroadcastReceiver).onSelect {
            val page = view!!.findViewById<ViewPager>(com.makentoshe.boorupostview.R.id.viewpager).currentItem
            val total = calculator.getItemsCountTotal(requireContext())
            val position = total * page + it
            navigator.navigateToImageFragment(position, booru, tags)
        }
    }

    /** Gets a viewmodel component */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = PostsFragmentViewModel.Factory(requireActivity().application, tags, newSearchBroadcastReceiver)
        viewmodel = ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // attach a content fragment
        attachFragment(com.makentoshe.boorupostview.R.id.contentview) { PostsContentFragment.build(booru, tags) }
        // attach a panel fragment
        attachFragment(com.makentoshe.boorupostview.R.id.panelview) { PostsPanelFragment.build(booru, tags) }
        // creates a presenter
        val presenter = PostsFragmentRxPresenter(disposables, viewmodel)
        //bind a toolbar
        val toolbar = view.findViewById<Toolbar>(com.makentoshe.boorupostview.R.id.toolbar_view)
        toolbar.title = booru.title
        toolbar.subtitleResource = com.makentoshe.boorupostview.R.string.posts
        //bind an option icon (magnify/close)
        val optionIcon = view.findViewById<View>(R.id.magnify_cross_view)
        presenter.bindOptionIcon(optionIcon)
        //bind a sliding up/down panel
        val slidingPanel = view.findViewById<SlidingUpPanelLayout>(com.makentoshe.boorupostview.R.id.slidingPanel)
        presenter.bindSlidingPanel(slidingPanel)
    }

    /** Unregister receiver */
    override fun onDetach() {
        super.onDetach()
        requireActivity().unregisterReceiver(newSearchBroadcastReceiver)
        requireActivity().unregisterReceiver(postSelectBroadcastReceiver)
    }

    /** Handle on back pressed event and close panel if it was opened */
    override fun onBackPressed(): Boolean {
        val v = view?.findViewById<SlidingUpPanelLayout>(R.id.slidingPanel) ?: return false
        return if (v.panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            v.panelState = SlidingUpPanelLayout.PanelState.EXPANDED; true
        } else false
    }

    /** Attaches the fragment attached to the [container] */
    private fun attachFragment(container: Int, factory: () -> Fragment) {
        val f = childFragmentManager.findFragmentById(container)
        if (f != null) return else childFragmentManager.beginTransaction().add(container, factory()).commit()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val NAVIGATOR = "PostsFragmentNavigator"
        fun build(booru: Booru, tags: Set<Tag>, navigator: PostsFragmentNavigator): Fragment {
            val fragment = PostsFragment()
            fragment.booru = booru
            fragment.tags = tags
            fragment.navigator = navigator
            return fragment
        }
    }
}
