package com.makentoshe.viewmodel

import androidx.fragment.app.Fragment

/**
 * ViewModel supports updating on [Fragment.onCreateView]
 */
open class ViewModel: CoroutineViewModel() {

    /**
     * @param owner the fragment from this method was called.
     */
    open fun onCreateView(owner: Fragment) = Unit
}