//package com.makentoshe.booruchan.postsample.view
//
//import android.view.View
//import com.makentoshe.booruchan.R
//import com.makentoshe.booruchan.postsample.model.DownloadErrorController
//import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
//import com.makentoshe.booruchan.postsamples.FullScreenController
//import org.jetbrains.anko.*
//
//class PostSampleUiContent(
//    private val downloadErrorController: DownloadErrorController,
//    private val sampleDownloadController: SampleImageDownloadController,
//    private val fullScreenController: FullScreenController
//) : AnkoComponent<_RelativeLayout> {
//
//    private lateinit var contentview: _FrameLayout
//    private lateinit var contentBuilder: PostSampleUiContentBuilder
//
//    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
//        frameLayout {
//            contentview = this
//            id = R.id.postsample_content
//            visibility = View.GONE
//
//            contentBuilder = PostSampleUiContentBuilder(fullScreenController,this )
//
//            sampleDownloadController.onSampleLoadingFinished(::onSampleLoadingFinished)
//            sampleDownloadController.onSampleGifLoaded(::onSampleGifLoaded)
//            sampleDownloadController.onSampleWebmUrlLoaded(::onSampleWebmLoaded)
//            sampleDownloadController.onSampleImageLoaded(::onSampleImageLoaded)
//            sampleDownloadController.onSampleImageLoadingError(::onSampleLoadingError)
//
//        }.lparams(matchParent, matchParent)
//    }
//
//    private fun onSampleImageLoaded(byteArray: ByteArray) {
//        try {
//            contentBuilder.buildImageView(byteArray)
//        } catch (e: Exception) {
//            downloadErrorController.push(e)
//        }
//    }
//
//    private fun onSampleGifLoaded(byteArray: ByteArray) {
//        try {
//            contentBuilder.buildGifView(byteArray)
//        } catch (e: Exception) {
//            downloadErrorController.push(e)
//        }
//    }
//
//    private fun onSampleWebmLoaded(url: String) {
//        try {
//            contentBuilder.buildWebmView(url)
//        } catch (e: Exception) {
//            downloadErrorController.push(e)
//        }
//    }
//
//    private fun onSampleLoadingFinished() {
//        contentview.visibility = View.VISIBLE
//    }
//
//    private fun onSampleLoadingError(e: Exception) {
//        contentview.visibility = View.GONE
//    }
//}