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
            //Query is Default so Default Query  title = Movie
            val response = RetrofitInstance.getApi().getProducts(ConstantKey.apiKey, "Movie", nextPage)

            LoadResult.Page(
                data = response.body()!!.searches,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.body()!!.searches.isEmpty()) null else nextPage + 1
            )

        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}