package com.bugbd.omdb.repository

import androidx.lifecycle.MutableLiveData
import com.bugbd.omdb.repository.model.movieDetail.MovieDetailResponse
import com.bugbd.omdb.repository.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsRepository {
    private val responseLiveData = MutableLiveData<MovieDetailResponse>()

    fun getMovieDetails(
        movieId: String,
        apiKey: String
    ): MutableLiveData<MovieDetailResponse> {
        val call = RetrofitInstance.getApi().getMovieDetails(movieId, apiKey)
        call.enqueue(object : Callback<MovieDetailResponse?> {
            override fun onResponse(
                call: Call<MovieDetailResponse?>,
                response: Response<MovieDetailResponse?>
            ) {
                if (response.isSuccessful) {
                    responseLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return responseLiveData

    }
}