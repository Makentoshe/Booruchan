package com.makentoshe.booruchan.screen.posts

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.view.PostPageGridElementUiFactory

class PostPageGridAdapter(
    private val posts: List<Post>,
    private val uiFactory: PostPageGridElementUiFactory
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: uiFactory.createView(parent!!.context)
//        controllerFactory.createController(getItem(position)).bindView(view)
        return view
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}

class PostPageGridAdapterFactory(
    private val uiFactory: PostPageGridElementUiFactory
//    private val controllerFactory: PostPageGridElementControllerFactory
) {
    fun build(posts: List<Post>): BaseAdapter {
        return PostPageGridAdapter(posts, uiFactory)
    }
}

