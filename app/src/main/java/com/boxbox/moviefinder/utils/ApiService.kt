package com.boxbox.moviefinder.utils

import com.boxbox.moviefinder.data.MovieDetailResponse
import com.boxbox.moviefinder.data.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun getMovieById(
        @Query("apikey") apiKey: String,
        @Query("i") id: String
    ): Response<MovieDetailResponse>

    @GET(".")
    suspend fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") title: String
    ): Response<MovieSearchResponse>
}