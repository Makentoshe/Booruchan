package com.makentoshe.booruchan.postinfo

import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostInfoUiContent(private val style: Style = Booruchan.INSTANCE.style) :
    AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        listView {
            id = R.id.postinfo_content
        }.lparams(matchParent, matchParent) {
            below(R.id.postinfo_toolbar)
        }
    }
}