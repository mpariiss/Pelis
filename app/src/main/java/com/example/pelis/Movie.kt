package com.example.pelis

import com.google.gson.annotations.SerializedName

class Movie(
val Title: String,
val Year: String,
val imdbID: String,
val Poster: String,
val Plot: String,
val Runtime: String,
val Director: String,
val Genre: String,
val Country: String,
)

data class MovieSearchResult(
    @SerializedName("Search")
    val search: List<Movie>?,
    val totalResults: String?,
    val Response: String?
)