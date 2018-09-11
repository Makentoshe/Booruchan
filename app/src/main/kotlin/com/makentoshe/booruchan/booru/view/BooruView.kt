package com.makentoshe.booruchan.booru.view

import android.content.Context
import android.content.Intent

interface BooruView {

    fun getIntent(): Intent

    fun getContext(): Context

}