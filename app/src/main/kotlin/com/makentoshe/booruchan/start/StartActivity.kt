package com.makentoshe.booruchan.start

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartActivityView().setContentView(this)
    }
}
