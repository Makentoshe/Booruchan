package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import androidx.core.util.Consumer
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.screen.posts.model.PostPageGridAdapter
import org.jetbrains.anko.find

class PostPageGridViewInflater(private val posts: List<Post>) : Consumer<View> {
    override fun accept(view: View) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        progress.visibility = View.GONE

        val gridview = view.find<GridView>(R.id.posts_page_gridview)
        gridview.visibility = View.VISIBLE
        gridview.adapter = PostPageGridAdapter(view.context, posts)
    }
}