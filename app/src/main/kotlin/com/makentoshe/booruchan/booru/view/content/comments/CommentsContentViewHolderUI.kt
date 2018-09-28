package com.makentoshe.booruchan.booru.view.content.comments

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.postDataScore
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.postDataTags
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7._CardView
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout

class CommentsContentViewHolderUI(private val style: Style) : AnkoComponent<ViewGroup> {

    @SuppressLint("NewApi")
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        frameLayout {
            cardView {
                radius = 0f
                forLollipop { elevation = 4f }
                createPostView()

            }.lparams(matchParent, wrapContent) {
                setMargins(dip(8), dip(8), dip(8), dip(8))
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
            id = Id.postPreviewImageView
            visibility = View.GONE
        }.lparams(dip(100), dip(100)) {
            startToStart = PARENT_ID
            topToTop = PARENT_ID
            setMargins(dip(8), dip(8), dip(8), 0)
        }
    }

    private fun _ConstraintLayout.createPostDataView() {
        verticalLayout {
            id = Id.postDataLayout
            visibility = View.GONE

            createPostTimeCreationView()
            createPostScoreView()
            createPostTagsView()

        }.lparams(width = dip(0), height = dip(100)) {
            startToEnd = Id.postPreviewImageView
            topToTop = PARENT_ID
            endToEnd = PARENT_ID
            setMargins(dip(8), dip(8), dip(8), 0)
        }

    }

    private fun _LinearLayout.createPostTimeCreationView() {
        textView {
            id = Id.postDataDate
            singleLine = true
            setTypeface(null, Typeface.BOLD)
        }
    }

    private fun _LinearLayout.createPostScoreView() {
        linearLayout {
            imageView {
                backgroundColorResource = R.color.MaterialGrey500
            }.lparams(width = dip(0), height = matchParent) {
                weight = 1f
            }
            textView {
                setPadding(dip(4), 0, 0, 0)
                id = postDataScore
                setTypeface(null, Typeface.BOLD)
            }.lparams(width = dip(0), height = matchParent) {
                weight = 10f
            }
        }
    }

    private fun _LinearLayout.createPostTagsView() {
        textView {
            id = postDataTags
            textSize = 12f
            maxLines = 3
            ellipsize = TextUtils.TruncateAt.MARQUEE
        }.lparams(width = matchParent, height = wrapContent) {
            topMargin = dip(4)
        }
    }

    private fun _ConstraintLayout.createCommentsView() {
        verticalLayout {
            id = Id.commentsLayout
            visibility = View.GONE
        }.lparams(width = matchParent, height = wrapContent) {
            topToBottom = Id.postDataLayout
            endToEnd = PARENT_ID
            startToStart = PARENT_ID
            setMargins(dip(20), 0, dip(4), dip(8))
        }
    }

    private fun _ConstraintLayout.createProgressBarView() {
        verticalLayout {
            id = Id.progressBar
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
            cardView {
                radius = 0f
                lparams(width = matchParent, height = wrapContent)

                verticalLayout {

                    linearLayout {
                        textView {
                            id = Ids.creator
                            setTypeface(null, Typeface.BOLD)
                        }.lparams { weight = 1f }

                        linearLayout {
                            gravity = Gravity.END
                            textView {
                                id = Ids.createdAt
                                setTypeface(null, Typeface.BOLD)
                            }
                        }.lparams { weight = 1f }
                    }

                    textView {
                        id = Ids.body
                    }

                }.lparams(width = matchParent) { margin = dip(4) }
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

    object Id {
        const val postPreviewImageView = 1
        const val postDataLayout = 2
        const val postDataDate = 3
        const val postDataScore = 5
        const val postDataTags = 6
        const val commentsLayout = 7
        const val progressBar = 8
    }

}