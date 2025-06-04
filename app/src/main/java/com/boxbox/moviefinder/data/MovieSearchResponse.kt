package com.boxbox.moviefinder.data

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse (
    @SerializedName("Search") val movies: List<MovieResponse>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String?
)