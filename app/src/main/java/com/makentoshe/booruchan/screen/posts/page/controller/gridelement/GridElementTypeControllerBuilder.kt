package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.component.post.Post

class GridElementTypeControllerBuilder {
    fun buildController(post: Post): GridElementTypeController {
        return GridElementTypeController(post)
    }
}