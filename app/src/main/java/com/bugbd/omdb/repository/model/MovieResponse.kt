package com.bugbd.omdb.repository.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val searches: List<Search>,
    @SerializedName("totalResults")
    val totalResults: String
)