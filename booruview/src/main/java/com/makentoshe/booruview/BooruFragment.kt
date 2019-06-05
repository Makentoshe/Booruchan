package com.makentoshe.booruview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.booruview.navigation.BooruFragmentNavigator
import com.makentoshe.navigation.FragmentNavigator
import com.makentoshe.navigation.Router
import org.jetbrains.anko.AnkoContext
import ru.terrakok.cicerone.Cicerone

class BooruFragment : Fragment() {

    private val containerId = com.makentoshe.booruview.R.id.content

    private val panelId = com.makentoshe.booruview.R.id.panel

    private lateinit var fragmentNavigator: FragmentNavigator

    private var shouldSetScreenOnNavigatorAttach: Boolean = true

    private var navigator: BooruFragmentNavigator
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(NAVIGATOR, value)
        get() = arguments!!.get(NAVIGATOR) as BooruFragmentNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigator = FragmentNavigator(requireActivity(), containerId, childFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shouldSetScreenOnNavigatorAttach = savedInstanceState?.getBoolean(ATTACH, true) ?: true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragment = BooruPanelFragment.build()
        childFragmentManager.beginTransaction().add(panelId, fragment).commit()
    }

    override fun onStart() {
        super.onStart()
        navigator.setNavigator(fragmentNavigator)
        if (shouldSetScreenOnNavigatorAttach) {
            navigator.navigateToPosts()
            shouldSetScreenOnNavigatorAttach = false
        }
    }

    override fun onStop() {
        super.onStop()
        navigator.removeNavigator()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(ATTACH, shouldSetScreenOnNavigatorAttach)
    }

    companion object {
        private const val NAVIGATOR = "Navigator"
        private const val ATTACH = "ShouldStartScreenOnNavigatorAttach"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = BooruFragment()
            val cicerone = Cicerone.create(Router())
            fragment.navigator = BooruFragmentNavigator(cicerone, booru, tags)
            return fragment
        }
    }
}
