package com.bugbd.omdb.repository.network

import com.bugbd.omdb.repository.model.MovieResponse
import com.bugbd.omdb.repository.model.movieDetail.MovieDetailResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    /*  getSliders  movie  Banner*/
    @GET("/")
    suspend fun getSlider(
        @Query("s") title: String,
        @Query("apikey") apikey: String
    ): Response<MovieResponse>


    /*  Batman Movies
        query s: "Batman"
        apiKey : .......
        */
    @GET("/")
    suspend fun getBatmanMovies(
        @Query("s") title: String,
        @Query("apikey") apikey: String
    ): Response<MovieResponse>


    /*    Latest Movies
        query title: "movie" as you like
        year       : 2022*/
    @GET("/")
    suspend fun getLatestMovies(
        @Query("s") title: String,
        @Query("apikey") apikey: String,
        @Query("y") year: String
    ): Response<MovieResponse>


    //Pagination library use to get movie load Page by page
    @GET("/")
    suspend fun getProducts(
        @Query("apikey") apikey: String,
        @Query("s") title: String,
        @Query("page") page: Int,

        ): Response<MovieResponse>

    //Movie Id Wise Movie Details Information
    @GET("/")
    fun getMovieDetails(
        @Query("i") movieId: String,
        @Query("apikey") apikey: String

    ): Call<MovieDetailResponse>

}