package com.makentoshe.booruchan.postpreview.model

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.R
import com.makentoshe.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Adapter for the [android.widget.GridView]. Displays a post images.
 *
 * @param posts is a list of the [Post] instances. Each post contains the data for
 * preview image getting from the [previewsRepository].
 * @param previewsRepository contains the preview images.
 * @param coroutineScope for getting a preview images from [previewsRepository] in another thread.
 * @param disposables [Disposable]s container. After each onClear or onCreateView event container
 * will be cleared and all objects will be disposed.
 */
class GridViewAdapter(
    private val posts: Posts,
    private val previewsRepository: Repository<Post, ByteArray>,
    private val coroutineScope: CoroutineScope,
    private val disposables: CompositeDisposable
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: PreviewUi(
            getItem(position),
            disposables,
            previewsRepository,
            coroutineScope
        ).createView(parent.context)
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size

    private class PreviewUi(
        private val post: Post,
        private val disposables: CompositeDisposable,
        previewsRepository: Repository<Post, ByteArray>,
        coroutineScope: CoroutineScope
    ) {

        val imageDownloadController = ImageDownloadController(coroutineScope, previewsRepository).apply {
            action(post)
        }

        fun createView(context: Context): View = with(context) {
            cardView {
                radius = 0f
                elevation = dip(4).toFloat()

                relativeLayout {
                    padding = dip(4)

                    imageView {
                        id = R.id.preview

                        val disposable = imageDownloadController.subscribe {
                            if (it.hasData()) {
                                val byteArray = it.data
                                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                Handler(Looper.getMainLooper()).post { setImageBitmap(bitmap) }
                            } else {
                                TODO("Error: ${it.exception}")
                            }
                        }
                        disposables.add(disposable)

                    }.lparams(matchParent, matchParent)
                }
                lparams(dip(elementSide), dip(elementSide)) {
                    gravity = Gravity.CENTER
                }
            }
        }

        companion object {
            private const val elementSide = 100
            private const val elementMargin = 16
            private const val previewSize = elementSide - elementMargin
        }
    }
}

