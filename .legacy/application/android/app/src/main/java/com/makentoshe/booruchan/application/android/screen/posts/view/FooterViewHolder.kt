package com.makentoshe.booruchan.application.android.screen.posts.view

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.application.android.R

class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val text: TextView = view.findViewById(R.id.fragment_posts_footer_message)
    val progress: ProgressBar = view.findViewById(R.id.fragment_posts_footer_progress)
    val retry: Button = view.findViewById(R.id.fragment_posts_footer_retry)

    fun loading() {
        text.visibility = View.INVISIBLE
        retry.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    fun contentEnd() {
        progress.visibility = View.INVISIBLE
        retry.visibility = View.INVISIBLE
        text.text = itemView.resources.getString(R.string.posts_footer_content_end)
        text.visibility = View.VISIBLE
    }

    fun error(onRetryClickListener: (View) -> Unit) {
        progress.visibility = View.INVISIBLE
        retry.visibility = View.VISIBLE
        retry.setOnClickListener(onRetryClickListener)
        // TODO update error handling
        text.text = itemView.resources.getString(R.string.posts_footer_content_error)
        text.visibility = View.VISIBLE
    }
}