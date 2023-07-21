package com.makentoshe.booruchan.screen.boorucontent.ui.foundation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagUi
import com.makentoshe.library.uikit.foundation.SecondaryText
import com.makentoshe.library.uikit.theme.BooruchanTheme


@Composable
fun SearchTagChip(
    modifier: Modifier = Modifier,
    searchTagUi: SearchTagUi,
    onCloseIconClick: () -> Unit = {},
) = AssistChip(
    modifier = modifier,
    border = AssistChipDefaults.assistChipBorder(
        borderColor = BooruchanTheme.colors.dimmed,
    ),
    colors = AssistChipDefaults.assistChipColors(
        labelColor = BooruchanTheme.colors.foreground,
    ),
    onClick = {},
    trailingIcon = {
        Box(
            modifier = Modifier.size(24.dp).clickable { onCloseIconClick() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = BooruchanTheme.colors.foreground,
            )
        }
    },
    label = {
        SecondaryText(
            text = searchTagUi.title,
            color = BooruchanTheme.colors.foreground,
        )
    }
)
