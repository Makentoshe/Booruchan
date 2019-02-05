package com.makentoshe.booruchan.postpreviewspage.model

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
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class GridViewAdapter(
    private val posts: Posts,
    private val previewImageDownloadViewModel: PreviewImageDownloadController
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: PreviewUi(getItem(position), previewImageDownloadViewModel).createView(parent.context)
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size

    private class PreviewUi(
        private val post: Post,
        private val previewsImageDownloadController: PreviewImageDownloadController
    ) {

        fun createView(context: Context): View = with(context) {
            cardView {
                radius = 0f
                elevation = dip(4).toFloat()

                relativeLayout {
                    padding = dip(4)

                    imageView {
                        id = R.id.preview

                        previewsImageDownloadController.subscribe {
                            if (it.data != null) {
                                if (post.previewUrl == it.data.first) {
                                    val array = it.data.second
                                    Handler(Looper.getMainLooper()).post {
                                        setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.size))
                                    }
                                }
                            } else {
                                TODO("Error: ${it.exception}")
                            }
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

