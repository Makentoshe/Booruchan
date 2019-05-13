package com.makentoshe.booruchan.screen.posts.page.model

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.ConcreteTypeControllerFactory
import com.makentoshe.booruchan.screen.posts.page.controller.gridelement.GridElementControllerBuilder
import com.makentoshe.booruchan.screen.posts.page.view.GridElementUiBuilder
import org.jetbrains.anko.find

/**
 * Adapter creates and bindings grid's element views and grid's element controllers.
 *
 * @param posts is a lists with posts.
 * @param uiBuilder is a factory which creates a grid's element views.
 * @param controllerBuilder is a factory which creates a grid's element controllers.
 */
class GridAdapter(
    private val posts: List<Post>,
    private val uiBuilder: GridElementUiBuilder,
    private val controllerBuilder: GridElementControllerBuilder
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //create or reuse view
        val view = convertView ?: uiBuilder.createView(parent!!.context)

        val imageview = view.find<ImageView>(R.id.posts_page_gridview_element_image)
        val imageReceiveController = ImageViewController(imageview)

        val progressbar = view.find<ProgressBar>(R.id.posts_page_gridview_element_progress)
        val streamProgressBarController = StreamProgressBarController(progressbar)

        val typeView = view.find<ImageView>(R.id.posts_page_gridview_element_type)
        ConcreteTypeControllerFactory(typeView).build(getItem(position)).setType()

        val controller = controllerBuilder.createController(getItem(position))
        controller.bindControllers(imageReceiveController, streamProgressBarController)

        return view
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}