package com.makentoshe.booruchan.screen.samples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R

class SamplePageFragmentRouter(private val fragmentManager: FragmentManager) {

    fun add(fragment: Fragment) {
        fragmentManager.beginTransaction().add(R.id.samples_content, fragment).commit()
    }
}