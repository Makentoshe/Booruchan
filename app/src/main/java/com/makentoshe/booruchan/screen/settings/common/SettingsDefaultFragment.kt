package com.makentoshe.booruchan.screen.settings.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.arguments
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class SettingsDefaultFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    // Controller for nsfw setting behaviour
    private val nsfwSettingController by currentScope.inject<NsfwSettingController> {
        parametersOf(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsDefaultUi()
            .createView(AnkoContext.create(requireContext(), this))
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
