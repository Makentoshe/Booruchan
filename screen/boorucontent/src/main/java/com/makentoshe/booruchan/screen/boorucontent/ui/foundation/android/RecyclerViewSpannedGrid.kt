package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android

import android.content.Context
import android.util.TypedValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model.BooruPostPagingDataAdapter
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model.BoorucontentLoadStateAdapter
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.view.SpacesItemDecoration
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.view.SpannedGridLayoutManager
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.view.SpannedGridLayoutManagerLookup
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RecyclerViewVerticalSpannedGrid(
    modifier: Modifier,
    adapterContent: (BooruPostPagingDataAdapter) -> Unit,
) = AndroidView(
    modifier = modifier,
    factory = ::recyclerViewFactory,
    update = { recyclerView ->
        val concatAdapter = recyclerView.adapter as ConcatAdapter
        val adapter = concatAdapter.adapters.first { it is BooruPostPagingDataAdapter }
        adapterContent(adapter as BooruPostPagingDataAdapter)
    },
)

private fun recyclerViewFactory(context: Context) = RecyclerView(context).apply {
    val topPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics)
    this.setPadding(0, topPadding.toInt(), 0, 0)
    this.clipToPadding = false

    val adapter = BooruPostPagingDataAdapter()
    val footerAdapter = BoorucontentLoadStateAdapter { adapter.retry() }
    this.adapter = adapter.withLoadStateFooter(footerAdapter)

    val orientation = SpannedGridLayoutManager.Orientation.VERTICAL
    this.layoutManager = SpannedGridLayoutManager(orientation, 3).apply {
        spanSizeLookup = SpannedGridLayoutManagerLookup(adapter)
    }

    this.addItemDecoration(SpacesItemDecoration(8f))
}
