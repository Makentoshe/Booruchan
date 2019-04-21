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
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Cicerone
import java.io.Serializable

class BooruFragment : Fragment() {

    init {
        currentScope.get<Fragment>(named(BooruModule.fragmentStr)) { parametersOf(this) }
    }

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val navigatorViewModel by viewModel<LocalNavigatorViewModel> {
        val cicerone = currentScope.get<Cicerone<Router>>(named(BooruModule.ciceroneStr))
        val localRouter = currentScope.get<LocalRouter>()
        parametersOf(cicerone, localRouter)
    }

    private val fragmentNavigator by currentScope.inject<FragmentNavigator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //provides params to the scope
        currentScope.get<Booru>(named(BooruModule.booruStr)) { parametersOf(booru) }
        currentScope.get<Set<Tag>>(named(BooruModule.tagsStr)) { parametersOf(tags) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruUi().createView(AnkoContext.create(requireContext(), this))
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
