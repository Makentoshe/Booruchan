package com.makentoshe.booruchan.postsamplespageinfo

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*

class ViewHolderUiInfos(private val postDataConverter: PostDataConverter):
    AnkoComponent<LinearLayout> {
    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        verticalLayout {

            addElement(postDataConverter.getId(context))

            addElement(postDataConverter.getRating(context))

            addElement(postDataConverter.getScore(context)) {
                println("Clik on Scores")
            }
        }
    }

    private fun _LinearLayout.addElement(item: String, onClick: (View) -> Unit = {}) {
        verticalLayout {
            //set ripple effect
            val outValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            backgroundResource = outValue.resourceId

            setOnClickListener(onClick)

            textView {
                text = item
                gravity = Gravity.CENTER_VERTICAL
                minHeight = dip(40)
                setPadding(dip(16), dip(8), 0, dip(8))
            }
            //separator
            view { backgroundColor = Color.DKGRAY }.lparams(matchParent, 1)
        }
    }
}