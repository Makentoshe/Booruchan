package com.makentoshe.booruchan.screen.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.screen.settings.view.SettingsUi
import org.jetbrains.anko.AnkoContext

class SettingsFragment : Fragment() {

    private val nsfwSettingController by lazy {
        NsfwSettingController(this)
    }

    private val webmOnPlaceController by lazy {
        WebMPlayingOnPlaceController(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nsfwSettingController.onViewCreated()
        webmOnPlaceController.onViewCreated()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.settings) {
            nsfwSettingController.onResult(resultCode)
        }
    }
}