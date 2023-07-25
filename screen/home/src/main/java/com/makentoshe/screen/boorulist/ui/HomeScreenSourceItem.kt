package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.library.uikit.foundation.PrimaryText
import com.makentoshe.screen.boorulist.entity.SourceUiState

@Composable
internal fun SourceItem(
    sourceUiState: SourceUiState,
    onClick: () -> Unit,
) = Row(
    modifier = Modifier.fillMaxWidth().height(72.dp).clickable { onClick() },
    horizontalArrangement = Arrangement.SpaceBetween,
) {
    SourceItemContent(sourceUiState)

//    if (booruItemState.health == BooruItemHealthState.Loading) {
//        BoorulistItemHealthProgress()
//    }
}

@Composable
private fun SourceItemContent(sourceUiState: SourceUiState) = Column(
    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
) {
    PrimaryText(text = sourceUiState.title)

//    when (booruItemState.health) {
//        BooruItemHealthState.Error -> {
////            val text = stringResource(id = R.string.boorulist_booruitem_error, booruItemState.url)
////            SecondaryText(text = text, color = BooruchanTheme.colors.error)
//        }
//        BooruItemHealthState.Loading -> {
////            val text = stringResource(id = R.string.boorulist_booruitem_loading, booruItemState.url)
////            SecondaryText(text = text, color = BooruchanTheme.colors.opaque)
//        }
//        BooruItemHealthState.Ok -> {
////            val text = stringResource(id = R.string.boorulist_booruitem_ok, booruItemState.url)
////            SecondaryText(text = text, color = BooruchanTheme.colors.dimmed)
//        }
//    }
}

//@Composable
//private fun BoorulistItemHealthProgress() = Box(
//    modifier = Modifier.padding(24.dp),
//    contentAlignment = Alignment.Center,
//) {
//    IndeterminateProgressBar(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
//}