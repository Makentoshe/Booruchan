package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.library.resources.R
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentToolbarState
import com.makentoshe.library.uikit.foundation.TitleText
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun BoorucontentTopBar(
    state: BoorucontentState,
) = Column(modifier = Modifier.fillMaxWidth().background(BooruchanTheme.colors.background)) {
    Box(
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(start = 16.dp),
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

    Divider(color = BooruchanTheme.colors.separator, thickness = 1.dp)
}
