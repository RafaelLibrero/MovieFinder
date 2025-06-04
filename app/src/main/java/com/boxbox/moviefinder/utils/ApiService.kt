package com.boxbox.moviefinder.utils

import com.boxbox.moviefinder.data.MovieDetailResponse
import com.boxbox.moviefinder.data.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("i={id}")
    suspend fun getMovieById(@Path("id") id: String): MovieDetailResponse

    @GET(".")
    suspend fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") title: String
    ): Response<MovieSearchResponse>
}