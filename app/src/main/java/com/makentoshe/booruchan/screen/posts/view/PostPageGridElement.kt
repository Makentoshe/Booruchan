package com.makentoshe.booruchan.screen.posts.view

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.repository.RxRepository
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.*
import kotlin.random.Random

class PostPageGridElement : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            imageView {
                id = R.id.posts_page_gridview_element_image
                backgroundColor = Random.nextInt()
            }.lparams(matchParent, matchParent)
            lparams(dip(100), dip(100))
        }
    }
}