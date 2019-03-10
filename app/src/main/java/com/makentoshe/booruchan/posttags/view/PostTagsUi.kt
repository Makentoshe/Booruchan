//package com.makentoshe.booruchan.posttags.view
//
//import android.view.View
//import androidx.fragment.app.Fragment
//import com.makentoshe.booruchan.Booruchan
//import com.makentoshe.booruchan.R
//import com.makentoshe.booruchan.postpreviews.viewmodel.TagsViewModel
//import com.makentoshe.booruchan.posttags.model.PostTagsNavigator
//import com.makentoshe.booruchan.posttags.model.TagsBuildController
//import org.jetbrains.anko.AnkoComponent
//import org.jetbrains.anko.AnkoContext
//import org.jetbrains.anko.backgroundColorResource
//import org.jetbrains.anko.relativeLayout
//
//class PostTagsUi(
//    private val tagsController: TagsBuildController,
//    private val searchController: TagsViewModel,
//    private val navigator: PostTagsNavigator
//) : AnkoComponent<Fragment> {
//
//    private val style = Booruchan.INSTANCE.style
//
//    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
//        relativeLayout {
//            backgroundColorResource = style.background.backgroundColorRes
//            PostTagsUiToolbar(searchController, navigator).createView(AnkoContext.createDelegate(this))
//            PostTagsUiProgressBar().createView(AnkoContext.createDelegate(this))
//
//            tagsController.onTagsReceivedListener {
//                findViewById<View>(R.id.posttags_progressbar).visibility = View.GONE
//                if (it.hasData()) {
//                    PostTagsUiContent(it.data, searchController).createView(AnkoContext.createDelegate(this))
//                } else {
//                    val message = StringBuilder(context.getString(R.string.tags_download_error)).append("\n")
//                    message.append(it.exception.localizedMessage).append("\n")
//                    PostTagsUiMessage(message).createView(AnkoContext.createDelegate(this))
//                }
//            }
//        }
//    }
//}