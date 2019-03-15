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
    private val repository: Repository<Post, ByteArray>
) : BaseAdapter() {

    private var onSubscribe: ((Disposable) -> Unit)? = null

    private val observables = Array<Single<Bitmap>>(posts.size) {
        Single.just(posts[it])
            .subscribeOn(Schedulers.newThread())
            .map { repository.get(it) }
            .map { BitmapFactory.decodeByteArray(it, 0, it.size) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return convertView ?: PostPageGridElement().createView(AnkoContext.create(context)).apply {
            val imageview = find<ImageView>(R.id.posts_page_gridview_element_image)

            val disposable = observables[position]
                .doOnError {
                    it.printStackTrace()
                }.subscribe { bitmap ->
                    println(position)
                    imageview.setImageBitmap(bitmap)
                }

            onSubscribe?.invoke(disposable)
        }
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size

    fun setOnSubscribeListener(action: (Disposable) -> Unit): PostPageGridAdapter {
        onSubscribe = action
        return this
    }
}