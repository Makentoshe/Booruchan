package com.makentoshe.boorupostview.model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorupostview.presenter.GridElementPresenter
import com.makentoshe.boorupostview.view.GridElementUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext

/**
 * Adapter creates and binds a grid element views and a grid element controllers.
 *
 * @param posts is a list of a sources. Grid elements count is equals to the list size.
 * @param disposables disposable container.
 * @param controllerHolder holds a [GridElementController] instances which associated with a [Post] instance.
 */
class GridScrollElementAdapter(
    private val posts: List<Post>,
    private val disposables: CompositeDisposable,
    private val controllerHolder: GridElementControllerHolder
) : BaseAdapter() {

    /** Create a ui for a grid element and binds a presenter to it */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val post = getItem(position)
        // get a controller based on post instance. Also pass a disposables instance to avoid a memory leaks.
        val controller = controllerHolder.get(post to disposables)!!
        // create a presenter
        val presenter = GridElementPresenter(disposables, position, controller, post)
        // create or reuse view and binds it to the presenter
        val view = convertView ?: createView(parent.context)
        // bind a root view
        presenter.bindView(view)
        // bind a preview image view
        val imageview = view.findViewById<ImageView>(com.makentoshe.boorupostview.R.id.gridview_element_image)
        presenter.bindImageView(imageview)
        // bind a progress bar
        val progressbar = view.findViewById<ProgressBar>(com.makentoshe.boorupostview.R.id.gridview_element_progress)
        presenter.bindProgressbar(progressbar)

        return view
    }

    /** Creates a view for a grid element */
    private fun createView(context: Context): View {
        return GridElementUi().createView(AnkoContext.create(context))
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}
