package com.makentoshe.booruchan.booru.view

import android.os.Bundle
import com.makentoshe.booruchan.Activity
import org.jetbrains.anko.setContentView

class BooruActivity: Activity() {

    companion object {
        const val BOOR_EXTRA = "ExtraBoor"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BooruActivityUI(getAppSettings().getStyle()).setContentView(this)
    }

}