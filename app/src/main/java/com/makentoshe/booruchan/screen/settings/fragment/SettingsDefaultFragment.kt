package com.makentoshe.booruchan.screen.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.settings.controller.nsfw.NsfwAlertController
import com.makentoshe.booruchan.screen.settings.controller.nsfw.NsfwSettingController
import com.makentoshe.booruchan.screen.settings.view.SettingsDefaultUi
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope

class SettingsDefaultFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    // Controller for nsfw setting behaviour
    private val nsfwSettingController by currentScope.inject<NsfwSettingController>()

    // Controller displays alert dialog when nsfw setting changes at the first time
    private val nsfwSettingAlertController by currentScope.inject<NsfwAlertController>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set up a fragment instance for displaying alert dialog
        nsfwSettingAlertController.fragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsDefaultUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nsfwSettingController.bindView(view)
    }

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SettingsDefaultFragment().apply {
            this.position = position
        }
    }
}
