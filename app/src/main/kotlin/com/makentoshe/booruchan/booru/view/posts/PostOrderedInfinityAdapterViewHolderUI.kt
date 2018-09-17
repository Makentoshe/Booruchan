package com.makentoshe.booruchan.booru.view.posts

import android.annotation.SuppressLint
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.gallery.posts.PostOrderedInfinityAdapter
import com.makentoshe.booruchan.common.forLollipop
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7._CardView
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class PostOrderedInfinityAdapterViewHolderUI : AnkoComponent<ViewGroup> {


    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            id = R.id.booru_content_gallery_row
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            lparams(matchParent, dip(PostOrderedInfinityAdapter.ViewHolder.SIDE + 10))

            (0..2).forEach { _ ->
                createPostView()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun _LinearLayout.createPostView() {
        cardView {
            id = R.id.booru_content_gallery_post_main
            radius = 0f
            forLollipop {
                elevation = dip(4).toFloat()
            }

            createPostViewDetail()

        }.lparams(dip(PostOrderedInfinityAdapter.ViewHolder.SIDE), dip(PostOrderedInfinityAdapter.ViewHolder.SIDE)) {
            setMargins(dip(5), 0, dip(5), 0)
        }
    }

    private fun _CardView.createPostViewDetail() {
        constraintLayout {

            createPreviewView()

        }.lparams(matchParent, matchParent)
    }

    private fun _ConstraintLayout.createPreviewView() {
        imageView {
            id = R.id.booru_content_gallery_post_preview
        }.lparams(matchConstraint, matchConstraint) {
            topToTop = PARENT_ID
            bottomToBottom = PARENT_ID
            endToEnd = PARENT_ID
            startToStart = PARENT_ID
            setMargins(dip(5), dip(5), dip(5), dip(5))
        }
    }
}