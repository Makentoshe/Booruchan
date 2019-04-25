package com.makentoshe.booruchan.anko

import android.view.View
import androidx.fragment.app.Fragment

inline fun <reified T : View> Fragment.findOptional(id: Int): T? = view?.findViewById(id) as? T
