package com.makentoshe.booruchan.screen.posts.model

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.screen.posts.view.PostPageGridElement
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import java.io.*

class PostPageGridAdapter(
    private val context: Context,
    private val posts: List<Post>,
    private val picasso: Picasso
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: PostPageGridElement().createView(AnkoContext.create(context))

        val imageview = view.find<ImageView>(R.id.posts_page_gridview_element_image)
        picasso.load(getItem(position).previewUrl).into(imageview)

        return view
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}