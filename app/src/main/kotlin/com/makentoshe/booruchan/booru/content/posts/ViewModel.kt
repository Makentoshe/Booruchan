package com.makentoshe.booruchan.booru.content.posts

import android.arch.lifecycle.ViewModel
import android.support.v4.app.FragmentActivity

abstract class ViewModel : ViewModel() {

    abstract fun startSampleActivity(activity: FragmentActivity, itemId: Int)

}