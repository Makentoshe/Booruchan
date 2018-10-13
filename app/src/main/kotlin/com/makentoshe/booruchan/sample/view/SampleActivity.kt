package com.makentoshe.booruchan.sample.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.sample.SampleViewModel
import org.jetbrains.anko.setContentView

class SampleActivity: Activity() {

    companion object {
        const val BOORU_EXTRA = "Booru"
        const val START_ID = "Start Id"
        const val TAGS_EXTRA = "Tags"
    }

    private lateinit var viewModel: SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val factory = SampleViewModel.Factory(intent)
        viewModel = ViewModelProviders.of(this, factory)[SampleViewModel::class.java]
        super.onCreate(savedInstanceState)
        SampleActivityUI(getAppSettings().getStyle(), viewModel).setContentView(this)
    }

}