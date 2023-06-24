package com.makentoshe.booruchan.model

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.arguments(): Bundle {
    if (arguments == null) {
        arguments = Bundle()
    }
    return arguments!!
}