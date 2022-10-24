package com.bugbd.omdb.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bugbd.omdb.repository.SliderRepository
import com.bugbd.omdb.repository.model.MovieResponse

class SliderViewModel() : ViewModel() {
    private val sliderRepository = SliderRepository()

    suspend fun getSliders(title: String, apikey: String): MutableLiveData<MovieResponse> {
        return sliderRepository.getSliders(title, apikey)
    }

}