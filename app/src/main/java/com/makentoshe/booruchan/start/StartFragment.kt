package com.makentoshe.booruchan.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.StartFragmentViewModelFactory
import com.makentoshe.booruchan.Fragment
import org.jetbrains.anko.AnkoContext

class StartFragment : Fragment<StartFragmentViewModel>() {

    override fun buildViewModel(arguments: Bundle): StartFragmentViewModel {
        val router = Booruchan.INSTANCE.router
        val list = Booruchan.INSTANCE.booruList
        val factory = StartFragmentViewModel.Factory(router, list)
        return ViewModelProviders.of(this, factory)[StartFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return StartFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }
}