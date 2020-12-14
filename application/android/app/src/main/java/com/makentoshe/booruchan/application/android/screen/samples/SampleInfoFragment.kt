package com.makentoshe.booruchan.application.android.screen.samples

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makentoshe.booruchan.application.android.FullContentDownloadExecutor
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.model.SampleInfoAdapter
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Content
import com.makentoshe.booruchan.core.post.Post
import kotlinx.android.synthetic.main.fragment_sample_info2.*
import toothpick.ktp.delegate.inject
import java.io.File

class SampleInfoFragment : CoreFragment(), FullContentDownloadExecutor.DownloadListener {

    companion object {
        fun build(booruContextClass: Class<BooruContext>, post: Post): SampleInfoFragment {
            val fragment = SampleInfoFragment()
            fragment.arguments.booruclass = booruContextClass
            fragment.arguments.post = post
            return fragment
        }

        fun capture(level: Int, message: String) = Log.println(level, "SampleInfoFragment", message)
    }

    val arguments = Arguments(this)
    private val downloadExecutor by inject<FullContentDownloadExecutor>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_info2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.fragment_sample_vertical)
        fragment_sample_info_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) return
                val manager = (recyclerView.layoutManager as LinearLayoutManager)
                viewPager.isUserInputEnabled = manager.findFirstCompletelyVisibleItemPosition() == 0
            }
        })
        fragment_sample_info_recycler.adapter = SampleInfoAdapter(arguments.post, downloadExecutor)
    }

    // TODO replace by notifications
    override fun onFinishDownload(directory: File, result: Result<*>) {
        result.fold({
            val string = getString(R.string.content_download_success, directory.name)
            Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
        }, {
            if (it is FileAlreadyExistsException) {
                val string = getString(R.string.content_download_already, directory.name)
                Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
            } else {
                val string = getString(R.string.content_download_failure, directory.name)
                Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onStartDownload(directory: File, content: Content) = Unit

    class Arguments(fragment: SampleInfoFragment) : FragmentArguments(fragment) {

        var post: Post
            get() = fragmentArguments.getSerializable(POST) as Post
            set(value) = fragmentArguments.putSerializable(POST, value)

        var booruclass: Class<BooruContext>
            get() = fragmentArguments.getSerializable(CLASS) as Class<BooruContext>
            set(value) = fragmentArguments.putSerializable(CLASS, value)

        companion object {
            private const val POST = "post"
            private const val CLASS = "class"
        }
    }
}