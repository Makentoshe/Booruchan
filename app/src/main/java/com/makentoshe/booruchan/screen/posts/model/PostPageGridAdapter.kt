package com.makentoshe.booruchan.screen.posts.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.screen.posts.view.PostPageGridElement
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class PostPageGridAdapter(
    private val context: Context,
    private val posts: List<Post>,
    private val previewsRepository: Repository<Post, ByteArray>,
    private val samplesRepository: Repository<Post, ByteArray>,
    private val filesRepository: CachedRepository<Post, ByteArray>
) : BaseAdapter() {

    private var onSubscribe: ((Disposable) -> Unit)? = null

    //contains completed Single observables which will gets bitmaps from repositories
    private val observables = Array(posts.size) { index ->
        Single.just(posts[index])
            //performs mapping in new thread
            .subscribeOn(Schedulers.newThread())
            .map { getPreviewBitmap(it) ?: getSampleBitmap(it) ?: getFileBitmap(it) }
            //performs subscribe in main thread
            .observeOn(AndroidSchedulers.mainThread())
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
        return convertView ?: PostPageGridElement().createView(AnkoContext.create(context)).apply {
            val imageview = find<ImageView>(R.id.posts_page_gridview_element_image)
            //subscribes on bitmap receive
            val disposable = observables[position]
                .doOnError {
                    //calls when downloading failed
                    it.printStackTrace()
                }.subscribe { bitmap ->
                    //calls when downloading success
                    imageview.setImageBitmap(bitmap)
                }
            //send disposable to the listener
            onSubscribe?.invoke(disposable)
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