package com.makentoshe.boorupostview.presenter

import android.widget.ImageView

/**
 * Presenter component for a grid element.
 */
interface GridElementPresenter {

    /** Bind a [ImageView] as a preview image. Image receives as a bytearray and decodes to the bitmap*/
    fun bindPreview(view: ImageView)

}