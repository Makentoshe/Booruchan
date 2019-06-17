package com.makentoshe.booruview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.navigation.FragmentNavigator
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Fragment contains a DrawerLayout.
 * Panel contains a navigation items.
 * Content contains a screens.
 */
class BooruFragment : Fragment() {

    /** Booru instance saved in args */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    /** Current set of the tags saved in args */
    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Navigator */
    private var navigator: BooruFragmentNavigator
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(NAVIGATOR, value)
        get() = arguments!!.get(NAVIGATOR) as BooruFragmentNavigator

    /** Performs transactions */
    private lateinit var fragmentNavigator: FragmentNavigator

    /** Flag that control an initial screen appearing */
    private var shouldSetScreenOnNavigatorAttach: Boolean = true

    /** Get an initial screen state (is should be attached) */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shouldSetScreenOnNavigatorAttach = savedInstanceState?.getBoolean(ATTACH, true) ?: true
    }

    /** Setup a FragmentNavigator */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val containerId = com.makentoshe.booruview.R.id.content
        fragmentNavigator = FragmentNavigator(requireActivity(), containerId, childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    /** Setups a drawer panel */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val panelId = com.makentoshe.booruview.R.id.panel
        val fragment = BooruPanelFragment.build()
        childFragmentManager.beginTransaction().add(panelId, fragment).commit()
    }

    /** Attaches a navigator and shows an initial screen if should */
    override fun onStart() {
        super.onStart()
        navigator.setNavigator(fragmentNavigator)
        if (shouldSetScreenOnNavigatorAttach) {
            navigator.navigateToPosts(booru, tags)
            shouldSetScreenOnNavigatorAttach = false
        }
    }

    /** Detaches a navigator */
    override fun onStop() {
        super.onStop()
        navigator.removeNavigator()
    }

    /** Save a current initial screen state */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(ATTACH, shouldSetScreenOnNavigatorAttach)
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val NAVIGATOR = "Navigator"
        private const val ATTACH = "ShouldStartScreenOnNavigatorAttach"
        fun build(booru: Booru, tags: Set<Tag>, navigator: BooruFragmentNavigator): Fragment {
            val fragment = BooruFragment()
            fragment.navigator = navigator
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}

