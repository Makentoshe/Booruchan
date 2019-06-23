package com.makentoshe.boorupostview.model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorupostview.presenter.GridElementPresenter
import com.makentoshe.boorupostview.view.GridScrollElementUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext

/**
 * Adapter creates and bindings grid's element views and grid's element controllers.
 */
class GridScrollElementAdapter(
    private val posts: List<Post>, private val disposables: CompositeDisposable,
    private val viewModelHolder: GridElementViewModelHolder,
    private val controllerHolder: GridElementControllerHolder
) : BaseAdapter() {

    /** Create a ui for a grid element and binds a presenter to it */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val controller = controllerHolder.get(getItem(position) to disposables)!!
        // get a viewmodel
        val viewmodel = viewModelHolder.viewmodels[position]
        // create a presenter
        val presenter = GridElementPresenter(disposables, position, controller)
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
        return GridScrollElementUi().createView(AnkoContext.create(context))
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}
