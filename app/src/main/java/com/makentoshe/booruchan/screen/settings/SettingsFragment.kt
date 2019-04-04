package com.makentoshe.booruchan.screen.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.RequestCode
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SettingsFragment : Fragment() {

    private val nsfwSettingController by lazy {
        NsfwSettingController(this)
    }

    private val streamDownloadSettingsController by lazy {
        StreamDownloadSettingController(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        appSettings.setNsfwAlert(requireContext(), true)
        return SettingsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nsfwSettingController.onViewCreated()
        streamDownloadSettingsController.onViewCreated()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.settings) {
            nsfwSettingController.onResult(resultCode)
        }
    }
}

class StreamDownloadSettingController(private val fragment: Fragment) {

    private val checkbox by lazy {
        fragment.view?.find<CheckBox>(R.id.setting_stream_download_checkbox)!!
    }

    private val view by lazy {
        fragment.view?.find<View>(R.id.setting_stream_download)!!
    }

    private val context = fragment.requireContext()

    fun onViewCreated() {
        //set default setting
        checkbox.isChecked = AppSettings.getStreamingDownload(context)
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            onCheck(isChecked)
        }
        view.setOnClickListener {
            checkbox.isChecked = AppSettings.getStreamingDownload(context).not()
        }
    }

    private fun onCheck(isChecked: Boolean) = if (isChecked) check() else uncheck()

    private fun check() {
        AppSettings.setStreamingDownload(context, true)
    }

    private fun uncheck() {
        AppSettings.setStreamingDownload(context, false)
    }
}