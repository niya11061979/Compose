package com.skillbox.compose.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.compose.data.Characters
import com.skillbox.compose.network.CharacterApi


class CharacterDataSource(api: CharacterApi) : PagingSource<Int, Characters>() {
    private val _api = api

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        return try {
            val response = _api.loadCharacter().results
            val pageNumber = params.key ?: 0
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.isNotEmpty()) pageNumber + 1 else null
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

