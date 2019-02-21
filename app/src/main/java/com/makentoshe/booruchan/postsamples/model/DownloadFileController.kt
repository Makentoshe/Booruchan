package com.makentoshe.booruchan.postsamples.model

import android.view.View

/**
 * Interface for starting file downloading.
 */
interface DownloadFileController {

    /**
     * Starts file downloading.
     *
     * @param view is a button or icon view. As usually provides from onClickListener.
     * @param currentItem the current item from source starts from 0.
     */
    fun startDownload(view: View, currentItem: Int)
}