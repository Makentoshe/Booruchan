package com.makentoshe.booruchan.screen.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.start.controller.StartContentController
import com.makentoshe.booruchan.screen.start.controller.StartOverflowController
import com.makentoshe.booruchan.screen.start.view.StartFragmentUi
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope

class StartFragment : Fragment() {

    //controller for overflow popup menu
    private val overflowController: StartOverflowController by inject()

    //controller for fragment content
    private val contentController: StartContentController by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overflowController.bindView(requireContext(), view)
    }

    override fun onResume() {
        super.onResume()
        //update list view if setting was changed in settings screen
        contentController.bindView(requireContext(), view!!)
    }
}