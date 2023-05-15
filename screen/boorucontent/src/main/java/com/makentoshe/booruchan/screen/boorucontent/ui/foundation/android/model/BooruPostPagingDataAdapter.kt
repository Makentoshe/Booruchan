package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.load
import com.makentoshe.booruchan.library.resources.R
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi

class BooruPostPagingDataAdapter :
    PagingDataAdapter<BooruPreviewPostUi, BooruPostPagingDataAdapter.BooruPostViewHolder>(
        BooruPostUiDiffUtill()
    ) {

    override fun onBindViewHolder(holder: BooruPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooruPostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BooruPostViewHolder(layoutInflater.inflate(R.layout.booru_post_ui_item, parent, false))
    }

    class BooruPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textView = view.findViewById<TextView>(R.id.booru_post_ui_item_id)
        private val imageView = view.findViewById<ImageView>(R.id.booru_post_ui_image)

        fun bind(item: BooruPreviewPostUi?) {
            textView.text = item?.id.toString()
            if (item == null) return

            imageView.load(item.url) {
                listener(
                    onError = { _, it -> println("Error: ${it.throwable} ${item.url}") },
                )
            }
        }
    }

    class BooruPostUiDiffUtill : DiffUtil.ItemCallback<BooruPreviewPostUi>() {
        override fun areItemsTheSame(oldItem: BooruPreviewPostUi, newItem: BooruPreviewPostUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BooruPreviewPostUi, newItem: BooruPreviewPostUi): Boolean {
            return oldItem == newItem
        }

    }
}