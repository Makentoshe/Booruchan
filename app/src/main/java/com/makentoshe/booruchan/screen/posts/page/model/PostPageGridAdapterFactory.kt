package com.makentoshe.booruchan.screen.posts.page.model

import android.widget.BaseAdapter
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.PostPageGridElementControllerFactory
import com.makentoshe.booruchan.screen.posts.page.view.PostPageGridElementUiFactory

/**
 * Factory creates a grid adapter instances.
 *
 * @param uiFactory is a factory which creates a grid's element views.
 * @param controllerFactory is a factory which creates a grid's element controllers.
 */
class PostPageGridAdapterFactory(
    private val uiFactory: PostPageGridElementUiFactory,
    private val controllerFactory: PostPageGridElementControllerFactory
) {
    /**
     * Creates a grid adapter instance with the posts.
     */
    fun build(posts: List<Post>): BaseAdapter {
        return PostPageGridAdapter(posts, uiFactory, controllerFactory)
    }
}