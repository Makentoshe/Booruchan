package com.makentoshe.booruchan.view

import android.view.ViewManager
import com.google.android.material.chip.Chip
import org.jetbrains.anko.custom.ankoView


fun ViewManager.chip(init: Chip.() -> Unit) = ankoView({ Chip(it) }, 0, init)