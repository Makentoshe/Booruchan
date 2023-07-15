package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi
import javax.inject.Inject

class ErrorPagingSource @Inject constructor(
    private val throwable: Throwable,
) : PagingSource<Int, BooruPreviewPostUi>() {

    override fun getRefreshKey(state: PagingState<Int, BooruPreviewPostUi>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BooruPreviewPostUi> {
        return LoadResult.Error(throwable)
    }
}