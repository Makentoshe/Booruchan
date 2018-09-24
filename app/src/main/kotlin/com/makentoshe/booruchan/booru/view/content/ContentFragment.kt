package com.makentoshe.booruchan.booru.view.content

import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

abstract class ContentFragment : Fragment() {

    abstract fun onSearchStarted(): WeakReference<(String) -> Unit>
}