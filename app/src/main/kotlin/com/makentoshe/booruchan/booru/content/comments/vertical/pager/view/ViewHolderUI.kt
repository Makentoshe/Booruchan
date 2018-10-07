package com.makentoshe.booruchan.booru.content.comments.vertical.pager.view

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.forLollipop
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class ViewHolderUI : AnkoComponent<ViewGroup> {

    @SuppressLint("NewApi")
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        frameLayout {

            cardView {
                forLollipop { elevation = dip(4).toFloat() }
                radius = 0f

                constraintLayout {
                    createPostView()
                    createCommentsView()
                }.lparams(width = matchParent, height = wrapContent) { margin = dip(8) }

            }.lparams(width = matchParent, height = wrapContent) {
                setMargins(dip(8), dip(8), dip(8), 0)
            }
        }
    }

    private fun _ConstraintLayout.createPostView() {
        linearLayout {
            id = R.id.booru_content_comment_viewholder_postview

            imageView {
                id = R.id.booru_content_comment_viewholder_postview_preview
            }.lparams(width = dip(100), height = dip(100))

            linearLayout {
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.booru_content_comment_viewholder_postview_date
                }.lparams(width = matchParent, height = dip(0)) { weight = 1f }

                textView {
                    id = R.id.booru_content_comment_viewholder_postview_score
                }.lparams(width = matchParent, height = dip(0)) { weight = 1f }

                textView {
                    id = R.id.booru_content_comment_viewholder_postview_tags
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    maxLines = 3
                }.lparams(width = matchParent, height = dip(0)) { weight = 3f }

            }.lparams(width = matchParent, height = dip(100)) {
                leftMargin = dip(8)
            }
        }.lparams(width = matchParent, height = dip(100)) {
            endToEnd = PARENT_ID
            startToStart = PARENT_ID
            topToTop = PARENT_ID
        }
    }

    private fun _ConstraintLayout.createCommentsView() {
        verticalLayout {
            id = R.id.booru_content_comment_viewholder_commentsview
            backgroundColorResource = R.color.MaterialIndigo700
        }.lparams(width = matchConstraint, height = wrapContent) {
            topToBottom = R.id.booru_content_comment_viewholder_postview
            startToStart = PARENT_ID
            endToEnd = PARENT_ID
            bottomToBottom = PARENT_ID
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
                            id = R.id.booru_content_comment_viewholder_commentsview_comment_creator
                            setTypeface(null, Typeface.BOLD)
                        }.lparams { weight = 1f }

                        linearLayout {
                            gravity = Gravity.END
                            textView {
                                id = R.id.booru_content_comment_viewholder_commentsview_comment_createdAt
                                textSize = 12f
                                setTypeface(null, Typeface.BOLD)
                            }
                        }.lparams { weight = 1f }
                    }

                    textView {
                        id = R.id.booru_content_comment_viewholder_commentsview_comment_body
                    }

                }.lparams(width = matchParent) { margin = dip(4) }
            }
        }
    }
}