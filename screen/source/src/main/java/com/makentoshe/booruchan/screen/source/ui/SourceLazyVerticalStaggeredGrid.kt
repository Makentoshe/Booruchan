package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.makentoshe.booruchan.screen.source.entity.PreviewPostUiState
import com.makentoshe.booruchan.screen.source.viewmodel.ContentState
import com.makentoshe.booruchan.screen.source.viewmodel.SourceScreenState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun SourceLazyVerticalStaggeredGrid(
    screenState: SourceScreenState,
    contentState: ContentState.Success,
) {
    val previewPostItems = contentState.pagerFlow.collectAsLazyPagingItems()

    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
        columns = StaggeredGridCells.Fixed(3),
        contentPadding = PaddingValues(top = 12.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(count = previewPostItems.itemCount) { index ->
            val item = previewPostItems[index]
            if (item != null) SourcePreviewPostUi(item = item)
        }
    }
}

@Composable
private fun SourcePreviewPostUi(
    item: PreviewPostUiState,
    modifier: Modifier = Modifier,
) = Box(
    modifier = modifier
        .aspectRatio(1 / item.hwRatio, true)
        .sizeIn(minWidth = 128.dp, minHeight = 128.dp)
) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data(item.url)
        .size(Size.ORIGINAL)
        .build()

    SubcomposeAsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = request,
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        loading = {
            IndeterminateProgressBar(modifier = Modifier.wrapContentSize())
        }
    )
}