package com.bugbd.omdb.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bugbd.omdb.repository.model.MovieResponse
import com.bugbd.omdb.repository.network.RetrofitInstance
import javax.net.ssl.SSLHandshakeException

class SliderRepository {
    private val responseLiveData = MutableLiveData<MovieResponse>()

    suspend fun getSliders(title: String, apiKey: String): MutableLiveData<MovieResponse> {
        val response = RetrofitInstance.getApi().getSlider(title, apiKey)
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