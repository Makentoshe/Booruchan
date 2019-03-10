//package com.makentoshe.booruchan.posttags.view
//
//import android.view.View
//import com.makentoshe.booruapi.Tag
//import com.makentoshe.booruchan.R
//import com.makentoshe.booruchan._ChipGroup
//import com.makentoshe.booruchan.chip
//import com.makentoshe.booruchan.chipGroup
//import com.makentoshe.booruchan.postpreviews.viewmodel.TagsViewModel
//import org.jetbrains.anko.*
//import org.jetbrains.anko.sdk27.coroutines.onClick
//
//class PostTagsUiContent(
//    private val tags: Set<Tag>,
//    private val searchController: TagsViewModel
//) : AnkoComponent<_RelativeLayout> {
//
//    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
//        scrollView {
//            setPadding(0, 0, 0, dip(8))
//            chipGroup {
//                setPadding(dip(8), dip(10), dip(8), 0)
//                tags.forEach { buildChip(it) }
//            }.lparams(matchParent, wrapContent)
//        }.lparams(matchParent, matchParent) {
//            below(R.id.posttags_toolbar)
//        }
//    }
//
//    private fun _ChipGroup.buildChip(tag: Tag) {
//        chip {
//            text = tag.name
//            onClick {
//                isSelected = true
//                isCloseIconVisible = true
//                searchController.addTag(tag)
//            }
//            setOnCloseIconClickListener {
//                searchController.removeTag(tag)
//                isSelected = false
//                isCloseIconVisible = false
//            }
//        }
//    }
//}