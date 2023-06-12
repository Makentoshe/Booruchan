package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.library.resources.R
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentToolbarState
import com.makentoshe.library.uikit.foundation.TitleText
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun BoorucontentTopBar(
    state: BoorucontentState,
    event: (BoorucontentEvent) -> Unit,
) = Column(modifier = Modifier.fillMaxWidth().background(BooruchanTheme.colors.background)) {
    BoorucontentTopBarContent(state = state, event = event)
    Divider(color = BooruchanTheme.colors.separator, thickness = 1.dp)
}

@Composable
private fun BoorucontentTopBarContent(
    state: BoorucontentState,
    event: (BoorucontentEvent) -> Unit,
) = Row(
    modifier = Modifier.fillMaxWidth().height(56.dp)
) {

    Box(
        modifier = Modifier.size(56.dp).clickable { event(BoorucontentEvent.NavigationBack) },
        contentAlignment = Alignment.Center,
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
    }

    Row(
        modifier = Modifier.fillMaxSize().padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
        ) {
            when (val toolbarState = state.toolbarState) {
                is BoorucontentToolbarState.Content -> {
                    TitleText(text = toolbarState.title)
                }
                is BoorucontentToolbarState.Error -> {
                    TitleText(text = toolbarState.message)
                }
                BoorucontentToolbarState.Loading -> {
                    TitleText(text = stringResource(id = R.string.boorucontent_title_loading))
                }
            }
        }

        Box(
            modifier = Modifier.size(56.dp).clickable { event(BoorucontentEvent.NavigationSearchBottomSheet) },
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

    }
}
