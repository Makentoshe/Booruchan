package com.makentoshe.booruchan.application.android.screen.samples.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.makentoshe.booruchan.application.android.FullContentDownloadExecutor
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.post.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO replace Dexter with custom solution
class SampleInfoAdapter(
    private val post: Post, private val executor: FullContentDownloadExecutor
) : RecyclerView.Adapter<SampleInfoAdapter.ViewHolder>() {

    // TODO add viewholder factory
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Download -> inflater.inflate(R.layout.layout_download, parent, false)
            Comments -> inflater.inflate(R.layout.layout_comments, parent, false)
            Voting -> inflater.inflate(R.layout.layout_voting, parent, false)
            Rating -> inflater.inflate(R.layout.layout_rating, parent, false)
            Tags -> inflater.inflate(R.layout.layout_tags, parent, false)
            else -> throw Exception()
        }.let(::ViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = when (getItemViewType(position)) {
        Download -> onBindViewHolderDownload(holder, position)
        Comments -> onBindViewHolderComments(holder, position)
        Voting -> onBindViewHolderVoting(holder, position)
        Rating -> onBindViewHolderRating(holder, position)
        Tags -> onBindViewHolderTags(holder, position)
        else -> throw Exception()
    }

    private fun onBindViewHolderDownload(holder: ViewHolder, position: Int) {
        val downloadTextView = holder.itemView.findViewById<TextView>(R.id.layout_download_text)

//         TODO mb add permission rationale?
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionCheckResponse = ContextCompat.checkSelfPermission(holder.context, permission)
        if (permissionCheckResponse == PackageManager.PERMISSION_GRANTED) {
            downloadTextView.text = holder.context.getString(R.string.layout_download_permission_granted)
        } else {
            downloadTextView.text = holder.context.getString(R.string.layout_download_permission_requires)
        }

        val dexterListener = object : BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                downloadTextView.text = holder.context.getString(R.string.layout_download_permission_granted)
                executor.downloadPostFullContent(post)
            }
        }

        val downloadButton = holder.itemView.findViewById<Button>(R.id.layout_download_button)
        downloadButton.setOnClickListener {
            if (permissionCheckResponse == PackageManager.PERMISSION_GRANTED) {
                executor.downloadPostFullContent(post)
            } else {
                Dexter.withContext(it.context).withPermission(permission).withListener(dexterListener).check()
            }
        }
    }

    private fun onBindViewHolderComments(holder: ViewHolder, position: Int) {
        val commentsCount = holder.context.getString(R.string.layout_comments_count)
        val commentsText = holder.context.getString(R.string.layout_comments_text, commentsCount)
        holder.itemView.findViewById<TextView>(R.id.layout_comments_text).text = commentsText
        holder.itemView.findViewById<Button>(R.id.layout_comments_button).setOnClickListener {
            Toast.makeText(holder.context, R.string.not_implemented, Toast.LENGTH_LONG).show()
        }
    }

    private fun onBindViewHolderVoting(holder: ViewHolder, position: Int) {
        val scoreMetadata = if (post.score.downScore != null && post.score.upScore != null) {
            holder.context.getString(R.string.layout_voting_metadata, post.score.upScore, post.score.downScore)
        } else ""
        val votingText = holder.context.getString(R.string.layout_voting_text, post.score.score, scoreMetadata)
        holder.itemView.findViewById<TextView>(R.id.layout_voting_text).text = votingText
        val toggleGroup = holder.itemView.findViewById<MaterialButtonToggleGroup>(R.id.layout_voting_toggle)
        toggleGroup.addOnButtonCheckedListener { _, _, _ ->
            Toast.makeText(holder.context, R.string.not_implemented, Toast.LENGTH_LONG).show()
        }
    }

    private fun onBindViewHolderRating(holder: ViewHolder, position: Int) {
        val id = when (post.rating) {
            com.makentoshe.booruchan.core.post.Rating.SAFE -> R.id.layout_rating_safe
            com.makentoshe.booruchan.core.post.Rating.QUESTIONABLE -> R.id.layout_rating_questionable
            com.makentoshe.booruchan.core.post.Rating.EXPLICIT -> R.id.layout_rating_explicit
        }
        val toggleGroup = holder.itemView.findViewById<MaterialButtonToggleGroup>(R.id.layout_rating_toggle)
        toggleGroup.isSelectionRequired = true
        toggleGroup.check(id)
        toggleGroup.forEach { child -> child.isEnabled = child.id == id }
    }

    private fun onBindViewHolderTags(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<View>(R.id.layout_tags_general).visibility = View.VISIBLE

        val chips = holder.itemView.findViewById<ChipGroup>(R.id.layout_tags_general_chips)
        GlobalScope.launch(Dispatchers.IO) {
            post.tags.tags.forEach { text ->
                val chip = createChip(text, chips)
                launch(Dispatchers.Main) { chips.addView(chip) }
            }
        }
    }

    private fun createChip(tag: Text, parent: ViewGroup): Chip {
        val chip = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_posts_chip, parent, false)
        chip as Chip
        chip.isCloseIconVisible = false
        chip.isClickable = false
        chip.text = tag.text
        return chip
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = 5

    // TODO make several holders for each type using sealed
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context: Context = view.context
    }

    companion object {
        private const val Download = 0
        private const val Comments = 1
        private const val Voting = 2
        private const val Rating = 3
        private const val Tags = 4
    }
}