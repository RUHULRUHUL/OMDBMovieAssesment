package com.bugbd.omdb.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bugbd.omdb.repository.MovieDetailsRepository
import com.bugbd.omdb.repository.model.movieDetail.MovieDetailResponse

class MovieDetailsViewModel:ViewModel() {
    private val movieDetailsRepository = MovieDetailsRepository()

    fun getMovieDetails(
        movieId: String,
        apikey: String,
    ): MutableLiveData<MovieDetailResponse> {
        return movieDetailsRepository.getMovieDetails(movieId, apikey)
    }
}