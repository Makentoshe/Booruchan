package com.makentoshe.booruchan.postsample

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.ArgumentsHolder
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class PostSampleFragment : Fragment<PostSampleViewModel>() {

    override fun buildViewModel(arguments: Bundle): PostSampleViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)

        val holderArguments = ArgumentsHolder[this::class.java.simpleName.plus(position)]

        val postsRepository = holderArguments!![PostsRepository::class.java.simpleName] as PostsRepository
        val samplesRepository = holderArguments[SampleImageRepository::class.java.simpleName] as SampleImageRepository
        val factory = PostSampleViewModel.Factory(position, postsRepository, samplesRepository)
        return ViewModelProviders.of(this, factory)[PostSampleViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSampleUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }
}

class PostSampleUi(
    private val viewModel: PostSampleViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<androidx.fragment.app.Fragment> {
    override fun createView(ui: AnkoContext<androidx.fragment.app.Fragment>): View = with(ui) {
        relativeLayout {
            PostSampleUiProgressbar(viewModel, style).createView(AnkoContext.createDelegate(this))
            PostSampleUiSampleimage(viewModel, style).createView(AnkoContext.createDelegate(this))
            PostSampleUiMessageview(viewModel, style).createView(AnkoContext.createDelegate(this))
        }
    }
}

class PostSampleUiSampleimage(
    private val viewModel: PostSampleViewModel,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        subsamplingScaleImageView {
            id = R.id.postsample_imageview
            visibility = View.GONE
            backgroundColorResource = style.background.backgroundColorRes
            viewModel.onSampleDownloadedListener {
                setImage(ImageSource.bitmap(it))
                visibility = View.VISIBLE
            }
        }.lparams(matchParent, matchParent)
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }
}

class PostSampleUiMessageview(
    private val viewModel: PostSampleViewModel,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        textView {
            id = R.id.postsample_messageview
            gravity = Gravity.CENTER
            visibility = View.GONE

            viewModel.onDownloadingErrorListener {
                val sb = StringBuilder(context.getString(R.string.sample_download_error)).append("\n")
                sb.append(it).append("\n")
                sb.append(context.getString(R.string.tap_for_retry))
                text = sb
                visibility = View.VISIBLE
            }

            setOnClickListener {
                val progressbar = ui.owner.findViewById<ProgressBar>(R.id.postsample_progressbar)
                progressbar.visibility = View.VISIBLE
                it.visibility = View.GONE

                viewModel.loadPosts(viewModel.pagePosition)
            }

        }.lparams(matchParent, matchParent)
    }
}

class PostSampleUiProgressbar(
    private val viewModel: PostSampleViewModel,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        progressBar {
            id = R.id.postsample_progressbar
            isIndeterminate = true
            indeterminateDrawable?.setColorFilter(style.toolbar.getPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
            viewModel.onSampleDownloadedListener {
                visibility = View.GONE
            }
            viewModel.onDownloadingErrorListener {
                visibility = View.GONE
            }
        }.lparams {
            centerInParent()
        }
    }
}