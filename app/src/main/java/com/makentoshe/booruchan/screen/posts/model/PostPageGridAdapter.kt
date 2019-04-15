package com.makentoshe.booruchan.screen.posts.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.screen.posts.view.PostPageGridElementUi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import java.io.File

class PostPageGridAdapter(
    private val context: Context,
    private val posts: List<Post>,
    private val previewsRepository: Repository<Post, ByteArray>,
    private val samplesRepository: Repository<Post, ByteArray>,
    private val filesRepository: Repository<Post, ByteArray>
) : BaseAdapter() {

    private var onSubscribe: ((Disposable) -> Unit)? = null

    private val elementsContainer by lazy {
        GridElementsContainer().apply {
            Array(posts.size) { add(buildGridElement(posts[it], it)) }
        }
    }

    private fun buildGridElement(post: Post, position: Int): GridElement {
        val observable = Single.just(post)
            .subscribeOn(Schedulers.newThread())
            .map { getPreviewBitmap(it) ?: getSampleBitmap(it) ?: getFileBitmap(it) }
            .observeOn(AndroidSchedulers.mainThread())
        return GridElement(position, post, observable)
    }

    //Returns bitmap from previews repository or null
    private fun getPreviewBitmap(post: Post): Bitmap? {
        val byteArray = previewsRepository.get(post)
        return decodeByteArray(byteArray)
    }

    //Returns bitmap from samples repository or null
    private fun getSampleBitmap(post: Post): Bitmap? {
        val bytearray = samplesRepository.get(post)
        return decodeByteArray(bytearray)
    }

    private fun getFileBitmap(post: Post): Bitmap? {
        val bytearray = filesRepository.get(post)
        return decodeByteArray(bytearray)
    }

    //decodes bytearray to Bitmap
    private fun decodeByteArray(array: ByteArray?): Bitmap? {
        if (array == null) return null
        //trying to decode bytearray
        //if preview image can't be decoded - factory returns null
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return convertView ?: PostPageGridElementUi().createView(AnkoContext.create(context)).apply {
            val element = elementsContainer.get(position)
            val disposable = element.observable.subscribe { bitmap, throwable ->
                if (bitmap != null) onSuccess(element, bitmap, this) else onError(throwable)
            }
            //send disposable to the listener
            onSubscribe?.invoke(disposable)
        }
    }

    private fun onError(throwable: Throwable) {
        //calls when downloading failed
        throwable.printStackTrace()
    }

    private fun onSuccess(gridElement: GridElement, bitmap: Bitmap, view: View) {
        val imageview = view.find<ImageView>(R.id.posts_page_gridview_element_image)
        imageview.setImageBitmap(bitmap)

        val typeview = view.find<ImageView>(R.id.posts_page_gridview_element_type)
        when (File(gridElement.post.fileUrl).extension) {
            "webm" -> {
                typeview.setImageResource(R.drawable.ic_webm)
                typeview.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    setMargins(0, 0, context.dip(2), context.dip(2))
                }
            }
            "gif" -> {
                typeview.setImageResource(R.drawable.ic_gif)
            }
        }
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size

    //action lambda will be called every time when view subscribed on bitmap receive.
    fun setOnSubscribeListener(action: (Disposable) -> Unit): PostPageGridAdapter {
        onSubscribe = action
        return this
    }
}

class GridElement(
    val position: Int,
    val post: Post,
    val observable: Single<Bitmap?>
)

class GridElementsContainer {

    private val list = ArrayList<GridElement>()

    fun add(element: GridElement) {
        list.add(element)
    }

    fun get(index: Int): GridElement {
        return list[index]
    }

}