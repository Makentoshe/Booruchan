package com.makentoshe.booruchan.postpage.model

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.PreviewImageDownloadController
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class GridViewAdapter(
    private val posts: Posts,
    private val previewsDownloadController: PreviewImageDownloadController
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: PreviewUi(getItem(position), previewsDownloadController).createView(parent.context)
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size

    private class PreviewUi(
        private val post: Post,
        private val previewsDownloadController: PreviewImageDownloadController
    ) {

        fun createView(context: Context): View = with(context) {
            cardView {
                radius = 0f
                elevation = dip(4).toFloat()

                relativeLayout {
                    padding = dip(4)

                    imageView {
                        id = R.id.preview

                        previewsDownloadController.subscribe(DownloadResult(post)) {
                            if (it.data != null) setImageBitmap(it.data) else /*TODO(err)*/ Unit
                        }

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

