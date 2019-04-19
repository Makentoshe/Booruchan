package com.makentoshe.booruchan.screen.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.settings.controller.webm.WebmPlayerSettingController
import com.makentoshe.booruchan.screen.settings.view.SettingsWebmUi
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope

class SettingsWebmFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    private val webmPlayerSettingController by currentScope.inject<WebmPlayerSettingController>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsWebmUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        webmPlayerSettingController.bindView(view)
    }

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SettingsWebmFragment().apply {
            this.position = position
        }
    }
}
