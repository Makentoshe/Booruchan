package com.makentoshe.booruchan.screen.source.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.source.entity.PreviewPostUiState
import com.makentoshe.booruchan.screen.source.viewmodel.ContentState
import com.makentoshe.library.uikit.layout.spanned.SpanSize
import com.makentoshe.library.uikit.layout.spanned.SpannedVerticalGrid
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun SourceSpannedVerticalGrid(
    contentState: ContentState.Success,
) = SpannedVerticalGrid(
    modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize(),
    content = {
        items(
            spans = 3,
            flow = contentState.pagerFlow,
            key = { it.id },
            spanSize = { _, item -> calculateSpanSize(item) }
        ) { state ->
            BooruchanTheme {
                Text(
                    modifier = Modifier.fillMaxSize().border(1.dp, Color.Magenta),
                    text = "$state",
                    color = Color.Cyan
                )
            }
        }
    },
)

// If htwRatio more than border - there is a vertical image, so it should place in 2 spans vertically
private const val VERTICAL_RATIO_BORDER = 1.5

// If htwRatio less than border - there is a horizontal image, so it should place in 2 spans horizontally
private const val HORIZONTAL_RATIO_BORDER = 0.5

private fun calculateSpanSize(item: PreviewPostUiState?): SpanSize {
    if (item == null) return SpanSize(1, 1)

    return when {
        item.hwRatio >= VERTICAL_RATIO_BORDER -> {
            SpanSize(1, 2)
        }
        item.hwRatio <= HORIZONTAL_RATIO_BORDER -> {
            SpanSize(2, 1)
        }
        else -> {
            SpanSize(1, 1)
        }
    }
}