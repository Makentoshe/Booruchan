package com.makentoshe.booruchan.screen.posts

import android.view.View

class PostPageGridElementController {
    fun bindView(view: View) {
    }
}

class PostPageGridElementControllerFactory {

    fun createController(): PostPageGridElementController {
//        val downloadController = downloadControllerFactory.buildController()
//        downloadController.start(post)
        return PostPageGridElementController()
    }
}

