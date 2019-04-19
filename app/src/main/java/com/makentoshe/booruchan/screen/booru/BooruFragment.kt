package com.makentoshe.booruchan.screen.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.CiceroneFactory
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.screen.booru.view.BooruUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module
import java.io.Serializable

class BooruFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val localRouter by lazy { LocalRouter(booru, tags) }

    private val navigator by lazy {
        FragmentNavigator(requireActivity(), R.id.booru_drawer_content, childFragmentManager)
    }

    private val navigatorViewModel by viewModel<LocalNavigatorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        navigatorViewModel.localRouter = localRouter
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onResume() {
        super.onResume()
        navigatorViewModel.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorViewModel.removeNavigator()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(booru: Booru, tags: Set<Tag>) = BooruFragment().apply {
            this.booru = booru
            this.tags = tags
        }
    }
}
