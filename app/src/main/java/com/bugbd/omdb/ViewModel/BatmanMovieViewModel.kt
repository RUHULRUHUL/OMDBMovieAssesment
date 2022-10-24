package com.bugbd.omdb.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bugbd.omdb.repository.BatmanMovieRepository
import com.bugbd.omdb.repository.model.MovieResponse

class BatmanMovieViewModel : ViewModel() {

    private val batmanMovieRepository = BatmanMovieRepository()

    suspend fun getBatmanMovies(title: String, apikey: String): MutableLiveData<MovieResponse> {
        return batmanMovieRepository.getBatmanMovies(title, apikey)
    }

}