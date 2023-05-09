package com.makentoshe.library.uikit.foundation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = BooruchanTheme.colors.foreground,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
) = Text(
    text = text,
    modifier = modifier,
    style = BooruchanTheme.typography.titleText,
    color = color,
    textAlign = textAlign,
    maxLines = maxLines,
)

@Composable
fun PrimaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = BooruchanTheme.colors.foreground,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
) = Text(
    text = text,
    modifier = modifier,
    style = BooruchanTheme.typography.primaryText,
    color = color,
    textAlign = textAlign,
    maxLines = maxLines,
)

@Composable
fun PrimaryTextBold(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
) = Text(
    text = text,
    modifier = modifier,
    style = BooruchanTheme.typography.primaryTextBold,
    color = color,
    textAlign = textAlign,
    maxLines = maxLines,
)

@Composable
fun SecondaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = BooruchanTheme.colors.opaque,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
) = Text(
    text = text,
    modifier = modifier,
    style = BooruchanTheme.typography.secondaryText,
    color = color,
    textAlign = textAlign,
    maxLines = maxLines,
)
