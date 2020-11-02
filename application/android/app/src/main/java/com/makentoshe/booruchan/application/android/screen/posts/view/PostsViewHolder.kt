package com.makentoshe.booruchan.application.android.screen.posts.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = (view as ViewGroup).children.first() as TextView
}