package com.bugbd.omdb.repository

import androidx.lifecycle.MutableLiveData
import com.bugbd.omdb.repository.model.MovieResponse
import com.bugbd.omdb.repository.network.RetrofitInstance

class BatmanMovieRepository {
    private val responseLiveData = MutableLiveData<MovieResponse>()

    suspend fun getBatmanMovies(title: String, apiKey: String): MutableLiveData<MovieResponse> {
        val response = RetrofitInstance.getApi().getBatmanMovies(title, apiKey)
        try {
            if (response.isSuccessful) {
                responseLiveData.postValue(response.body())
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return responseLiveData

    }
}