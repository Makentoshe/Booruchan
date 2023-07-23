package com.makentoshe.library.uikit.extensions

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
@SuppressLint("ComposableNaming")
fun <T> Flow<T>.collectLatestInComposable(
    effectKey: Any = Unit,
    composeCoroutineScope: CoroutineScope = rememberCoroutineScope(),
    action: suspend (T) -> Unit,
) = LaunchedEffect(key1 = effectKey) {
    // We using Main immediate because we suppose that if collect function must be called from ui
    // it should works on Main thread
    composeCoroutineScope.launch(Dispatchers.Main.immediate) { collectLatest { action(it) } }
}