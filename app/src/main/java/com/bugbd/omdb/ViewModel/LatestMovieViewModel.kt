package com.bugbd.omdb.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bugbd.omdb.repository.LatestMoveRepository
import com.bugbd.omdb.repository.model.MovieResponse


class LatestMovieViewModel : ViewModel() {
    private val latestMoveRepository = LatestMoveRepository()

    suspend fun getLatestMovies(
        title: String,
        apikey: String,
        year: String
    ): MutableLiveData<MovieResponse> {
        return latestMoveRepository.getLatestMovies(title, apikey, year)
    }
}