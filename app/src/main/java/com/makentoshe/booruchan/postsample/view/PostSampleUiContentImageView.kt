package com.makentoshe.booruchan.postsample.view

import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewManager
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.matchParent

class PostSampleUiContentImageView(private val byteArray: ByteArray) : AnkoComponent<_FrameLayout> {
    override fun createView(ui: AnkoContext<_FrameLayout>): View = with(ui.owner) {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        subsamplingScaleImageView {
            setImage(ImageSource.bitmap(bitmap))

            setOnClickListener {
                println("ASA")
            }

        }.lparams(matchParent, matchParent)
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }
}