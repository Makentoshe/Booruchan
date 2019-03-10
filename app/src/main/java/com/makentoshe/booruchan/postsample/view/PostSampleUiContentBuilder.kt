//package com.makentoshe.booruchan.postsample.view
//
//import android.view.View
//import com.makentoshe.booruchan.postsamples.FullScreenController
//import org.jetbrains.anko.AnkoContext
//import org.jetbrains.anko._FrameLayout
//
//class PostSampleUiContentBuilder(
//    private val contentController: FullScreenController,
//    private val owner: _FrameLayout
//) {
//
//    fun buildImageView(byteArray: ByteArray) {
//        PostSampleUiContentImageView(byteArray)
//            .createView(AnkoContext.createDelegate(owner))
//            .apply(::onClickListener)
//    }
//
//    fun buildGifView(byteArray: ByteArray) {
//        PostSampleUiContentGifView(byteArray)
//            .createView(AnkoContext.createDelegate(owner))
//            .apply(::onClickListener)
//    }
//
//    fun buildWebmView(webmUrl: String) {
//        PostSampleUiContentWebmView(webmUrl)
//            .createView(AnkoContext.createDelegate(owner))
//            .apply(::onClickListener)
//    }
//
//    private fun onClickListener(view: View) {
//        view.setOnClickListener {
//            contentController.perform()
//        }
//    }
//}