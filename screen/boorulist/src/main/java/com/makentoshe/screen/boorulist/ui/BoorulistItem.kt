package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import com.makentoshe.library.uikit.foundation.PrimaryText
import com.makentoshe.library.uikit.foundation.SecondaryText
import com.makentoshe.library.uikit.theme.BooruchanTheme
import com.makentoshe.library.uikit.theme.error
import com.makentoshe.screen.boorulist.BooruItemHealthState
import com.makentoshe.screen.boorulist.BooruItemState

@Composable
internal fun BoorulistItem(
    booruItemState: BooruItemState,
) = Row(
    modifier = Modifier.fillMaxWidth().height(72.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
) {
    // Booru title and url
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        PrimaryText(text = booruItemState.title)

        if (booruItemState.health is BooruItemHealthState.Error) {
            SecondaryText(
                text = booruItemState.url,
                color = BooruchanTheme.colors.error,
            )
        } else {
            SecondaryText(
                text = booruItemState.url,
                color = BooruchanTheme.colors.dimmed,
            )
        }
    }

    // Progress bar at the end
    when (booruItemState.health) {
        BooruItemHealthState.Loading -> BoorulistItemHealthProgress()
        else -> Unit
    }
}

@Composable
private fun BoorulistItemHealthProgress() = Box(
    modifier = Modifier.padding(24.dp),
    contentAlignment = Alignment.Center,
) {
    IndeterminateProgressBar(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
}