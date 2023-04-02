package com.bugbd.omdb.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bugbd.omdb.repository.model.Search
import com.bugbd.omdb.repository.network.RetrofitInstance
import com.bugbd.omdb.repository.utils.ConstantKey
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource : PagingSource<Int, Search>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val nextPage = params.key ?: 1
            val response =
                RetrofitInstance.getApi().getProducts(ConstantKey.apiKey, "Movie", nextPage)
            LoadResult.Page(
                data = response.body()!!.searches,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.body()!!.searches.isEmpty()) null else nextPage + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}