package com.makentoshe.booruchan.screen.start

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.screen.start.inflator.StartUiInflatorContent
import com.makentoshe.booruchan.screen.start.inflator.StartUiInflatorOverflow
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import com.makentoshe.booruchan.screen.start.view.StartFragmentUi
import org.jetbrains.anko.AnkoContext

class StartFragment : Fragment() {

    private val navigator by lazy {
        StartScreenNavigator(Booruchan.INSTANCE.router)
    }

    private val booruList by lazy {
        Booruchan.INSTANCE.booruList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        StartUiInflatorOverflow(navigator).inflate(view)
        StartUiInflatorContent(navigator, booruList).inflate(view)
    }
}