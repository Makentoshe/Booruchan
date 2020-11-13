package com.makentoshe.booruchan.application.android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class FragmentArguments<F: Fragment>(private val fragment: F) {

    init {
        val fragment = fragment as Fragment
        if (fragment.arguments == null) {
            fragment.arguments = Bundle()
        }
    }

    protected val fragmentArguments: Bundle
        get() = fragment.requireArguments()
}
