package com.bugbd.omdb.repository

import androidx.lifecycle.MutableLiveData
import com.bugbd.omdb.repository.model.MovieResponse
import com.bugbd.omdb.repository.network.RetrofitInstance

class LatestMoveRepository {

    private val responseLiveData = MutableLiveData<MovieResponse>()

    suspend fun getLatestMovies(
        title: String,
        apiKey: String,
        year: String
    ): MutableLiveData<MovieResponse> {
        val response = RetrofitInstance.getApi().getLatestMovies(title, apiKey, year)
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