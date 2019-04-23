package com.makentoshe.booruchan.screen.posts.page.model

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.PostPageGridElementControllerFactory
import com.makentoshe.booruchan.screen.posts.page.view.PostPageGridElementUiFactory

/**
 * Adapter creates and bindings grid's element views and grid's element controllers.
 *
 * @param posts is a lists with posts.
 * @param uiFactory is a factory which creates a grid's element views.
 * @param controllerFactory is a factory which creates a grid's element controllers.
 */
class PostPageGridAdapter(
    private val posts: List<Post>,
    private val uiFactory: PostPageGridElementUiFactory,
    private val controllerFactory: PostPageGridElementControllerFactory
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //create or reuse view
        val view = convertView ?: uiFactory.createView(parent!!.context)
        //bind view with the controller
        controllerFactory.createController(getItem(position)).bindView(view)
        return view
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}

