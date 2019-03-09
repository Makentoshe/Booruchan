package com.makentoshe.booruchan.screen.posts.view

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.repository.RxRepository
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.*
import kotlin.random.Random

class PostPageGridElement(
//    private val repository: RxRepository<Post, ByteArray>,
//    private val disposables: CompositeDisposable
) : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            imageView {
                backgroundColor = Random.nextInt()
//                repository.onComplete {
//                    println("Complete")
//                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
//                    setImageBitmap(bitmap)
//                }
            }.lparams(matchParent, matchParent)
            lparams(dip(100), dip(100))
        }
    }
}