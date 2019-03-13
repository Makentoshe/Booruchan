package com.makentoshe.booruchan.screen.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.booru.inflator.BooruInflatorPanel
import com.makentoshe.booruchan.screen.booru.model.LocalNavigatorHolder
import com.makentoshe.booruchan.screen.booru.model.LocalNavigatorImpl
import com.makentoshe.booruchan.screen.booru.view.BooruUi
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class BooruFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val router by lazy {
        LocalNavigatorHolder.create(this, LocalNavigatorImpl(booru))
    }

    private val navigator by lazy {
        FragmentNavigator(requireActivity(), R.id.booru_drawer_content, childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        BooruInflatorPanel(router).accept(view)
    }

    override fun onResume() {
        super.onResume()
        router.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        router.removeNavigator()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(booru: Booru, tags: Set<Tag>): Fragment {
            return BooruFragment().apply {
                this.booru = booru
                this.tags = tags
            }
        }
    }
}