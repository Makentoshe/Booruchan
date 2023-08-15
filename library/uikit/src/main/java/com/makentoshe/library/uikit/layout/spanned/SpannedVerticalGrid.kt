package com.makentoshe.library.uikit.layout.spanned

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.compositionContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.library.uikit.theme.BooruchanTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SpannedVerticalGrid(
    modifier: Modifier,
    content: SpannedVerticalGridScope.() -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val compositionContext = rememberCompositionContext()
    AndroidView(
        modifier = modifier.background(BooruchanTheme.colors.background),
        factory = { context -> recyclerViewFactory(context, compositionContext, coroutineScope, content) },
    )
}

class SpannedVerticalGridScope(
    private val compositionContext: CompositionContext,
    private val recyclerView: RecyclerView,
    private val coroutineScope: CoroutineScope,
) {
    fun <T : Any> items(
        spans: Int,
        data: PagingData<T>,
        key: ((item: T) -> Any),
        spanSize: (index: Int, item: T?) -> SpanSize = { _, _ -> SpanSize(1, 1) },
        itemContent: @Composable (item: T) -> Unit,
    ) {
        val adapter = SpannedPagingDataAdapter(compositionContext, key, itemContent)
        recyclerView.adapter = adapter

        coroutineScope.launch(Dispatchers.IO) {
            adapter.submitData(data)
        }

        val orientation = SpannedGridLayoutManager.Orientation.VERTICAL
        this.recyclerView.layoutManager = SpannedGridLayoutManager(orientation, spans).apply {
            spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
                spanSize(position, adapter.peek(position))
            }
        }
    }
}

private fun recyclerViewFactory(
    context: Context,
    compositionContext: CompositionContext,
    coroutineScope: CoroutineScope,
    content: SpannedVerticalGridScope.() -> Unit,
) = RecyclerView(context).apply {
    // set paddings
    val topPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics)
    this.setPadding(0, topPadding.toInt(), 0, 0)
    this.clipToPadding = false

    content.invoke(SpannedVerticalGridScope(compositionContext, this, coroutineScope))
}

class SpannedPagingDataAdapter<T : Any>(
    private val compositionContext: CompositionContext,
    key: (item: T) -> Any,
    private val itemContent: @Composable (item: T) -> Unit,
) : PagingDataAdapter<T, SpannedPagingDataAdapter.ViewHolder<T>>(
    SpannedDiffUtill(key)
) {

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val composeView = ComposeView(parent.context)
        composeView.compositionContext = this.compositionContext
        return ViewHolder(composeView, itemContent)
    }

    class ViewHolder<T : Any>(
        private val containerView: ComposeView,
        private val itemContent: @Composable (item: T) -> Unit,
    ) : RecyclerView.ViewHolder(containerView) {

        fun bind(item: T) {
            containerView.setContent {
                itemContent(item)
            }
        }
    }

    class SpannedDiffUtill<T : Any>(private val key: ((item: T) -> Any)) : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return key(oldItem) == key(newItem)
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}
