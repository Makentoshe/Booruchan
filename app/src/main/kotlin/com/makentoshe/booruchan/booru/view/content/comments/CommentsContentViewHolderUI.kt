package com.makentoshe.booruchan.booru.view.content.comments

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.common.forLollipop
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
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            this.text = text
        }.lparams(matchParent, wrapContent) {
            weight = 2f
        }
    }

    private fun _ConstraintLayout.createCommentsView() {
        verticalLayout {
            id = Ids.commentsLayout
            visibility = View.GONE
        }.lparams(width = matchParent, height = wrapContent) {
            topToBottom = Ids.postDataLayout
            endToEnd = PARENT_ID
            startToStart = PARENT_ID
            setMargins(dip(20), 0, dip(4), dip(8))
        }
    }

    private fun _ConstraintLayout.createProgressBarView() {
        verticalLayout {
            id = Ids.progressBar
            gravity = Gravity.CENTER
            horizontalProgressBar {
                isIndeterminate = true
                max = 1
            }.lparams(matchParent, dip(25))
        }.lparams(matchParent, dip(100)) {
            setMargins(dip(8), dip(4), dip(8), 0)
        }

    }

    class CommentUIBuilder : AnkoComponent<View> {

        @SuppressLint("NewApi")
        override fun createView(ui: AnkoContext<View>): View = with(ui) {
            frameLayout {

                cardView {
                    radius = 0f
                    forLollipop {
                        elevation = dip(4).toFloat()
                    }
                    verticalLayout {

                        linearLayout {
                            textView {
                                id = Ids.creator
                                setTypeface(null, Typeface.BOLD)
                            }.lparams {weight = 1f}

                            linearLayout {
                                gravity = Gravity.END
                                textView {
                                    id = Ids.createdAt
                                    setTypeface(null, Typeface.BOLD)
                                }
                            }.lparams {weight = 1f}
                        }

                        textView {
                            id = Ids.body
                        }

                    }.lparams(width = matchParent) { margin = dip(4) }

                }.lparams(width = matchParent, height = wrapContent) {
                    setMargins(dip(4), dip(4), dip(4), dip(8))
                }

                lparams(width = matchParent, height = wrapContent)
            }
        }

        class Ids {
            companion object {
                const val body = 1
                const val creator = 2
                const val createdAt = 3
            }
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
            const val progressBar = 8
        }
    }

}