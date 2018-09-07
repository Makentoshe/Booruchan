package com.makentoshe.booruchan.appsettings

import android.os.Bundle
import com.makentoshe.booruchan.Activity
import com.makentoshe.booruchan.styles.AstarteStyle
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.setContentView

class AppSettingsActivity: Activity() {

    val style: Style = AstarteStyle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppSettingsActivityView(style).setContentView(this)
    }

}