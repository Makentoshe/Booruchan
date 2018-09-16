package com.makentoshe.booruchan.booru.model.gallery.post.ordinf

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.makentoshe.booruchan.common.forLollipop
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class PostOrderedInfinityAdapter : RecyclerView.Adapter<PostOrderedInfinityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PostOrderedInfinityGalleryUI()
                        .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    class PostOrderedInfinityGalleryUI : AnkoComponent<ViewGroup> {

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
                    }.lparams(dip(ViewHolder.SIDE), dip(ViewHolder.SIDE)) {
                        setMargins(dip(5), 0, dip(5), 0)
                    }
                }

                lparams(matchParent, dip(ViewHolder.SIDE + 10)) 
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            const val SIDE = 110
        }

    }
}