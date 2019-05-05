package com.makentoshe.booruchan.screen.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.settings.controller.NsfwSettingController
import com.makentoshe.booruchan.screen.settings.view.SettingsDefaultUi
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SettingsDefaultFragment : Fragment() {

    // Controller for nsfw setting behaviour
    private val nsfwSettingController by inject<NsfwSettingController> {
        parametersOf(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsDefaultUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nsfwSettingController.bindView(view)
    }

    companion object {
        fun create(position: Int) = SettingsDefaultFragment()
    }
}
