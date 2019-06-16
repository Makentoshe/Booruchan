package com.makentoshe.boorupostview.model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.makentoshe.api.*
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.StreamDownloadListener
import com.makentoshe.boorupostview.presenter.GridElementRxPresenter
import com.makentoshe.boorupostview.view.GridScrollElementUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Adapter creates and bindings grid's element views and grid's element controllers.
 *
 * @param posts is a lists with posts.
 * @param repositoryBuilder creates a repository instance for getting a preview images by post
 */
class GridScrollElementAdapter(
    private val posts: List<Post>,
    private val disposables: CompositeDisposable,
    private val repositoryBuilder: PreviewImageRepository.Builder
) : BaseAdapter() {

    /** Build a preview repository and wrap it by cache decorator */
    private fun buildPreviewRepository(
        listener: StreamDownloadListener, context: Context
    ): Repository<Post, ByteArray> {
        val networkExecutor = NetworkExecutorBuilder.buildStreamGet(listener)
        val repository = repositoryBuilder.build(networkExecutor)
        val cache = ImageDiskCache(DiskCache(ImageDiskCache.getPreviewDir(context)))
        return RepositoryCache(cache, repository)
    }

    /** Create a ui for a grid element and binds a presenter to it */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listener = SimpleStreamDownloadListener()
        val repository = buildPreviewRepository(listener, parent.context)
        val presenter = GridElementRxPresenter(disposables, repository, getItem(position), listener)
        // create or reuse view
        val view = convertView ?: createView(parent.context)
        // bind a preview image view
        val imageview = view.find<ImageView>(com.makentoshe.boorupostview.R.id.gridview_element_image)
        presenter.bindPreview(imageview)

        //        val progressbar = view.find<ProgressBar>(R.id.posts_page_gridview_element_progress)
        //        val streamProgressBarController = StreamProgressBarController(progressbar)

        //        val typeView = view.find<ImageView>(R.id.posts_page_gridview_element_type)
        //        ConcreteTypeControllerFactory(typeView).build(getItem(position)).setType()

        //        val controller = controllerBuilder.createController(getItem(position))
        //        controller.bindControllers(imageReceiveController, streamProgressBarController)

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