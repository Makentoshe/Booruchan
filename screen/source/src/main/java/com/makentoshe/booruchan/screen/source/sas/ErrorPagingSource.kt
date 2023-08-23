package com.makentoshe.booruchan.screen.source.sas

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makentoshe.booruchan.screen.source.entity.PreviewPostUiState
import javax.inject.Inject

class ErrorPagingSource @Inject constructor(
    private val throwable: Throwable,
) : PagingSource<Int, PreviewPostUiState>() {

    override fun getRefreshKey(state: PagingState<Int, PreviewPostUiState>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PreviewPostUiState> {
        return LoadResult.Error(throwable)
    }
}