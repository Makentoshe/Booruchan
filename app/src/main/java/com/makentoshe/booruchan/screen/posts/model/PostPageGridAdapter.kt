package com.makentoshe.booruchan.screen.posts.model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.view.PostPageGridElement
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class PostPageGridAdapter(
    private val context: Context,
    private val posts: List<Post>
) : BaseAdapter() {

    init {
        println("init")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: PostPageGridElement().createView(AnkoContext.create(context))
        val imageview = view.find<ImageView>(R.id.posts_page_gridview_element_image)
        Glide.with(view).asBitmap().load(posts[position].previewUrl).into(imageview)
        return view
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}