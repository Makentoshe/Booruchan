package com.makentoshe.booruchan.booru.view.content.comments

import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7._CardView
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout

class CommentsContentViewHolderUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        frameLayout {
            cardView {
                createPostView()

            }.lparams(matchParent, wrapContent) {
                margin = dip(8)
            }
            lparams(matchParent, wrapContent)
        }
    }

    private fun _CardView.createPostView() {
        constraintLayout {
            createProgressBarView()
            createPostPreviewView()
            createPostDataView()
            createCommentsView()
        }.lparams(matchParent, matchParent)

    }

    private fun _ConstraintLayout.createPostPreviewView() {
        imageView {
            id = Ids.postPreviewImageView
            visibility = View.GONE
        }.lparams(dip(100), dip(100)) {
            startToStart = PARENT_ID
            topToTop = PARENT_ID
            setMargins(dip(8), dip(8), dip(8), 0)
        }
    }

    private fun _ConstraintLayout.createPostDataView() {
        verticalLayout {
            id = Ids.postDataLayout
            visibility = View.GONE

            createPostSingleDataView(Ids.postDataDate, "Date: today epta")
            createPostSingleDataView(Ids.postDataUser, "User: SasAi")
            createPostSingleDataView(Ids.postDataScore, "Score: 8")
            createPostSingleDataView(Ids.postDataTags, "Tags: sky skirt sas anus psa this is a long tag list in one string")

        }.lparams(width = dip(0), height = dip(100)) {
            startToEnd = Ids.postPreviewImageView
            topToTop = PARENT_ID
            endToEnd = PARENT_ID
            setMargins(dip(8), dip(8), dip(8), 0)
        }

    }

    private fun _LinearLayout.createPostSingleDataView(id: Int, text: String) {
        textView {
            this.id = id
            singleLine = true
            this.text = text
        }.lparams(matchParent, wrapContent) {
            weight = 2f
        }
    }

    private fun _ConstraintLayout.createCommentsView() {
        verticalLayout {
            id = Ids.commentsLayout
            visibility = View.GONE
        }.lparams(width = matchParent, height = dip(300)) {
            endToEnd = PARENT_ID
            startToStart = PARENT_ID
        }
    }

    private fun _ConstraintLayout.createProgressBarView() {
        verticalLayout {
            gravity = Gravity.CENTER
            horizontalProgressBar {
                isIndeterminate = true
                max = 1
                id = Ids.progressBar
            }.lparams(matchParent, dip(25))
        }.lparams(matchParent, dip(100)) {
            setMargins(dip(8), 0, dip(8), 0)
        }

    }

    class Ids {
        companion object {
            const val postPreviewImageView = 1
            const val postDataLayout = 2
            const val postDataDate = 3
            const val postDataUser = 4
            const val postDataScore = 5
            const val postDataTags = 6
            const val commentsLayout = 7
            const val progressBar = 7
        }
    }

}