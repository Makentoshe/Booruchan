package com.makentoshe.booruchan.appsettings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.styles.AstarteStyle
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.setContentView

class AppSettingsActivity: AppCompatActivity() {

    val style: Style = AstarteStyle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppSettingsActivityView(AstarteStyle()).setContentView(this)
    }

}