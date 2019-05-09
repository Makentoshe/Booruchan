package com.makentoshe.booruchan.screen.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.booru.view.BooruUi
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Cicerone
import java.io.Serializable

class BooruFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val navigatorViewModel by viewModel<LocalNavigatorViewModel> {
        parametersOf(booru, tags)
    }

    private val fragmentNavigator by inject<FragmentNavigator> {
        parametersOf(requireActivity(), childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onResume() {
        super.onResume()
        navigatorViewModel.setNavigator(fragmentNavigator)
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
