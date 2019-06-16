package com.makentoshe.boorupostview.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.makentoshe.api.Repository
import com.makentoshe.api.SimpleStreamDownloadListener
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Reactive presenter component for a grid element.
 */
class GridElementRxPresenter(
    override val disposables: CompositeDisposable,
    private val repository: Repository<Post, ByteArray>,
    post: Post, listener: SimpleStreamDownloadListener
) : RxPresenter(), GridElementPresenter {

    /** On image download success */
    private val imageObservable = BehaviorSubject.create<Bitmap>()
    /** On image download error */
    private val errorObservable = BehaviorSubject.create<Throwable>()

    init {
        // downloads a preview image
        Single.just(post).observeOn(Schedulers.io()).map {
            val bytes = repository.get(it)!!
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }.observeOn(AndroidSchedulers.mainThread()).subscribe { b, t ->
            if (t != null) return@subscribe errorObservable.onNext(t)
            if (b != null) return@subscribe imageObservable.onNext(b)
            errorObservable.onNext(Exception("wtf"))
        }.let(disposables::add)
    }

    override fun bindPreview(view: ImageView) {
        // on success
        imageObservable.subscribe {
            view.visibility = View.VISIBLE
            view.setImageBitmap(it)
        }.let(disposables::add)
    }

    override fun bindRoot(view: View) = view.setOnClickListener {
        println("SAS")
    }
}