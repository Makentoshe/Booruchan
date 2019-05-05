package com.makentoshe.booruchan.screen.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.settings.controller.SettingsPageController
import com.makentoshe.booruchan.screen.settings.view.SettingsPageUi
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject

/* Container for concrete fragments settings */
class SettingsPageFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    private val contentController by inject<SettingsPageController>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsPageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentController.bindView(position, childFragmentManager)
    }

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SettingsPageFragment().apply {
            this.position = position
        }
    }
}
