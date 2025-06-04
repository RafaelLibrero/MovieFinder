package com.boxbox.moviefinder.data

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("imdbID") val id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String
)