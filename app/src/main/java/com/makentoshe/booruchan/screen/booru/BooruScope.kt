package com.makentoshe.booruchan.screen.booru

import com.makentoshe.booruchan.model.CiceroneFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

fun Module.buildBooruScope() {
    factory { CiceroneFactory() }
    viewModel { LocalNavigatorViewModel(get()) }
}
