package com.bugbd.omdb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.bugbd.omdb.repository.network.ApiServices
import com.bugbd.omdb.repository.paging.MoviePagingSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiServices: ApiServices) {
    fun getProducts() = Pager(
        config = PagingConfig(10, 10, true, 10, 100),
        pagingSourceFactory = { MoviePagingSource() }
    ).liveData
}