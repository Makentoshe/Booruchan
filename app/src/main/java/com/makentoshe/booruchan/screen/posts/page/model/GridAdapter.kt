package com.makentoshe.booruchan.screen.posts.page.model

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.GridElementControllerBuilder
import com.makentoshe.booruchan.screen.posts.page.view.PostPageGridElementUiBuilder

/**
 * Adapter creates and bindings grid's element views and grid's element controllers.
 *
 * @param posts is a lists with posts.
 * @param uiBuilder is a factory which creates a grid's element views.
 * @param controllerBuilder is a factory which creates a grid's element controllers.
 */
class GridAdapter(
    private val posts: List<Post>,
    private val uiBuilder: PostPageGridElementUiBuilder,
    private val controllerBuilder: GridElementControllerBuilder
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //create or reuse view
        val view = convertView ?: uiBuilder.createView(parent!!.context)
        //bind view with the controller
        controllerBuilder.createController(getItem(position)).bindView(view)
        return view
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}

