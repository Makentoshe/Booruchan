package com.makentoshe.booruchan.postpage.model

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class GridViewAdapter(
    private val posts: Posts
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: PreviewUi(position, getItem(position)).createView(parent.context)
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size

    class PreviewUi(
        private val position: Int,
        private val post: Post
    ) {

        fun createView(context: Context): View = with(context) {
            cardView {
                radius = 0f
                elevation = dip(4).toFloat()

                relativeLayout {
                    padding = dip(4)

                    imageView {
                        id = R.id.preview

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