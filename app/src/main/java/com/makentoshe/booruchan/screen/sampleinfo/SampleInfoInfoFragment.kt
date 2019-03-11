package com.makentoshe.booruchan.screen.sampleinfo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.jetbrains.anko.backgroundColor

class SampleInfoInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply {
            backgroundColor = Color.CYAN
            text = "Info"
        }
    }

    companion object {
        fun create() = SampleInfoInfoFragment()
    }
}