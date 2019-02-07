package com.makentoshe.booruchan.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.booru.view.BooruFragmentUI
import org.jetbrains.anko.AnkoContext

class BooruFragment : Fragment<BooruFragmentViewModel>() {

    override fun buildViewModel(arguments: Bundle): BooruFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        //if was called from sample - the search must be started with this tags
        val tags = arguments.getSerializable(Tag::class.java.simpleName) as Set<Tag>

        val factory = BooruFragmentViewModel.Factory(booru, tags)
        return ViewModelProviders.of(this, factory)[BooruFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return BooruFragmentUI(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }
//
//    override fun onBackPressed(): Boolean {
//        //close drawer
//        if (viewModel.getDrawerState() == DrawerState.DrawerOpen) {
//            viewModel.closeDrawer()
//            return true
//        }
//        //check inner fragment on backpress
//        val fragment = childFragmentManager.fragments.last()
//        return fragment is Backpressable && fragment.onBackPressed()
//    }
}