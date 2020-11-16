package com.makentoshe.booruchan.application.android.fragment

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

abstract class CoreFragment : Fragment() {

    fun hideSoftKeyboard(view: View) {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showSoftKeyboard() {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }
}