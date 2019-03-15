package com.makentoshe.booruchan.screen.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.router
import com.makentoshe.booruchan.screen.start.inflator.StartUiInflatorContent
import com.makentoshe.booruchan.screen.start.inflator.StartUiInflatorOverflow
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import com.makentoshe.booruchan.screen.start.view.StartFragmentUi
import org.jetbrains.anko.AnkoContext

class StartFragment : Fragment() {

    private val navigator by lazy {
        StartScreenNavigator(router)
    }

    private val booruList by lazy {
        Booruchan.INSTANCE.booruList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        StartUiInflatorOverflow(navigator).accept(view)
        StartUiInflatorContent(navigator, booruList).accept(view)
    }
}