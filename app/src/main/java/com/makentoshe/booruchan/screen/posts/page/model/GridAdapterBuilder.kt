package com.makentoshe.booruchan.screen.posts.page.model

import android.widget.BaseAdapter
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.GridElementControllerBuilder
import com.makentoshe.booruchan.screen.posts.page.view.PostPageGridElementUiBuilder

/**
 * Factory creates a grid adapter instances.
 *
 * @param uiBuilder is a factory which creates a grid's element views.
 * @param controllerBuilder is a factory which creates a grid's element controllers.
 */
class GridAdapterBuilder(
    private val uiBuilder: PostPageGridElementUiBuilder,
    private val controllerBuilder: GridElementControllerBuilder
) {
    /**
     * Creates a grid adapter instance with the posts.
     */
    fun build(posts: List<Post>): BaseAdapter {
        return GridAdapter(posts, uiBuilder, controllerBuilder)
    }
}