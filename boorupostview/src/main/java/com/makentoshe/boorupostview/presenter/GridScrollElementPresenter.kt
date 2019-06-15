package com.makentoshe.boorupostview.presenter

import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView

/**
 * Presenter component for the grid scroll element.
 * User interface should contains grid view, progress bar and message view.
 */
interface GridScrollElementPresenter {

    /** Bind a [GridView] */
    fun bindGridView(view: GridView)

    /** Bind a [ProgressBar] */
    fun bindProgressBar(view: ProgressBar)

    /** Bind a [TextView] as a message view */
    fun bindMessageView(view: TextView)
}