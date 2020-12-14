package com.makentoshe.booruchan.application.android.screen.samples

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.application.android.FullContentDownloadExecutor
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Content
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.Rating
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type
import kotlinx.android.synthetic.main.layout_comments.*
import kotlinx.android.synthetic.main.layout_download.*
import kotlinx.android.synthetic.main.layout_rating.*
import kotlinx.android.synthetic.main.layout_tags.*
import kotlinx.android.synthetic.main.layout_voting.*
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
        return inflater.inflate(R.layout.fragment_sample_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnClickListener { /* requires for best touch experience */ }
        onViewCreatedDownload()
        onViewCreatedComments()
        onViewCreatedVoting()
        onViewCreatedRating()
        onViewCreatedTags()
    }

    private fun onViewCreatedDownload() {
        // TODO mb add permission rationale?
        val readWriteStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionCheckStatus = ContextCompat.checkSelfPermission(requireContext(), readWriteStoragePermission)
        if (permissionCheckStatus == PackageManager.PERMISSION_GRANTED) {
            layout_download_text.text = getString(R.string.layout_download_permission_granted)
        } else {
            layout_download_text.text = getString(R.string.layout_download_permission_requires)
        }
        layout_download_button.setOnClickListener {
            if (permissionCheckStatus == PackageManager.PERMISSION_GRANTED) {
                downloadExecutor.downloadPostFullContent(arguments.post)
            } else {
                requestPermissions(arrayOf(readWriteStoragePermission), 0)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissions.zip(grantResults.map { it == PackageManager.PERMISSION_GRANTED }.toTypedArray()).forEach {
            when (it.first) {
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                    if (it.second) layout_download_text.text = getString(R.string.layout_download_permission_granted)
                }
                else -> capture(Log.INFO, "${it.first}: ${it.second}")
            }
        }
    }

    private fun onViewCreatedComments() {
        val commentsCount = getString(R.string.layout_comments_count)
        layout_comments_text.text = getString(R.string.layout_comments_text, commentsCount)
        layout_comments_button.setOnClickListener {
            Toast.makeText(requireContext(), R.string.not_implemented, Toast.LENGTH_LONG).show()
        }
    }

    private fun onViewCreatedVoting() {
        val downScore = arguments.post.score.downScore
        val upScore = arguments.post.score.upScore
        val scoreMetadata = if (downScore != null && upScore != null) {
            getString(R.string.layout_voting_metadata, upScore, downScore)
        } else ""
        layout_voting_text.text = getString(R.string.layout_voting_text, arguments.post.score.score, scoreMetadata)
        layout_voting_toggle.addOnButtonCheckedListener { _, _, _ ->
            Toast.makeText(requireContext(), R.string.not_implemented, Toast.LENGTH_LONG).show()
        }
    }

    private fun onViewCreatedRating() {
        layout_rating_toggle.isSelectionRequired = true

        val id = when (arguments.post.rating) {
            Rating.SAFE -> R.id.layout_rating_safe
            Rating.QUESTIONABLE -> R.id.layout_rating_questionable
            Rating.EXPLICIT -> R.id.layout_rating_explicit
        }
        layout_rating_toggle.check(id)
        layout_rating_toggle.forEach { child -> child.isEnabled = child.id == id }
    }

    private fun onViewCreatedTags() {
        arguments.post.tags.tags.forEach { tag ->
            if (tag is Tag) when (tag.type) {
                Type.ARTIST -> onArtistTagDisplay(tag)
                Type.CHARACTER -> onCharacterTagDisplay(tag)
                Type.COPYRIGHT -> onCopyrightTagDisplay(tag)
                Type.METADATA -> onMetadataTagDisplay(tag)
                Type.GENERAL -> onGeneralTagDisplay(tag)
            } else {
                onGeneralTagDisplay(tag)
            }
        }
    }

    private fun onArtistTagDisplay(tag: Text) = onCustomTagDisplay(tag, layout_tags_artist, layout_tags_artist_chips)

    private fun onCharacterTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, layout_tags_character, layout_tags_character_chips)

    private fun onMetadataTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, layout_tags_metadata, layout_tags_metadata_chips)

    private fun onCopyrightTagDisplay(tag: Text) =
        onCustomTagDisplay(tag, layout_tags_copyright, layout_tags_copyright_chips)

    private fun onGeneralTagDisplay(tag: Text) = onCustomTagDisplay(tag, layout_tags_general, layout_tags_general_chips)

    private fun onCustomTagDisplay(tag: Text, group: View, chipGroup: ChipGroup) {
        group.visibility = View.VISIBLE
        chipGroup.addView(createChip(tag, chipGroup))
    }

    private fun createChip(tag: Text, parent: ViewGroup): Chip {
        val chip = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_search_posts_chip, parent, false)
        chip as Chip
        chip.isCloseIconVisible = false
        chip.text = tag.text
        return chip
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