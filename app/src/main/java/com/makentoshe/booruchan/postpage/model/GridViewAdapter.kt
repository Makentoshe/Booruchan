package com.makentoshe.booruchan.postpage.model

import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import java.lang.Exception

class GridViewAdapter(
    private val posts: Posts,
    private val previewsDownloadController: PreviewsDownloadController
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return try {
            buildView(convertView, parent) { view ->
                try {
                    previewsDownloadController.subscribe {
                        try {
                            if (position == it.first) {
                                val array = it.second
                                val bitmap = BitmapFactory.decodeByteArray(array, 0, array.size)
                                view.findViewById<ImageView>(R.id.preview).setImageBitmap(bitmap)
                            }
                        } catch (e: Exception) {
                        }
                    }
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size

    private fun buildView(convertView: View?, parent: ViewGroup, actionOnFirstCreate: (View) -> Unit): View {
        return convertView ?: parent.context.cardView {
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
        }.apply {
            if (convertView == null) actionOnFirstCreate(this)
        }
    }

    companion object {
        private const val elementSide = 100
        private const val elementMargin = 16
        private const val previewSize = elementSide - elementMargin
    }
}