package com.makentoshe.booruchan.application.android.screen.posts.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.makentoshe.booruchan.application.android.R

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val text: TextView = view.findViewById(R.id.fragment_posts_item_text)
    val preview: ImageView = view.findViewById(R.id.fragment_posts_item_preview)
    val shimmer: ShimmerFrameLayout = view.findViewById(R.id.fragment_posts_item_shimmer)
}