package com.makentoshe.booruchan.application.android.screen.posts.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.application.android.R

class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val text: TextView = view.findViewById(R.id.fragment_posts_item_text)
    val preview: ImageView = view.findViewById(R.id.fragment_posts_item_preview)
}