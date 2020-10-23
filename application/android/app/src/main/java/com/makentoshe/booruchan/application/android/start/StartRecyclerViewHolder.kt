package com.makentoshe.booruchan.application.android.start

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.application.android.R

class StartRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val primary: TextView = view.findViewById(R.id.fragment_start_item_primary)
    val secondary: TextView = view.findViewById(R.id.fragment_start_item_secondary)
}