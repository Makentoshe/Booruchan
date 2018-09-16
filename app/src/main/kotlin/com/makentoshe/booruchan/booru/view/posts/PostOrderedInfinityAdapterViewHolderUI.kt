package com.makentoshe.booruchan.booru.view.posts

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.makentoshe.booruchan.booru.model.gallery.posts.PostOrderedInfinityAdapter
import com.makentoshe.booruchan.common.forLollipop
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class PostOrderedInfinityAdapterViewHolderUI : AnkoComponent<ViewGroup> {

    @SuppressLint("NewApi")
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER

            for (i in 0 until 3 step 1) {
                cardView {
                    radius = 0f
                    forLollipop {
                        elevation = dip(4).toFloat()
                    }
                }.lparams(dip(PostOrderedInfinityAdapter.ViewHolder.SIDE), dip(PostOrderedInfinityAdapter.ViewHolder.SIDE)) {
                    setMargins(dip(5), 0, dip(5), 0)
                }
            }

            lparams(matchParent, dip(PostOrderedInfinityAdapter.ViewHolder.SIDE + 10))
        }
    }
}