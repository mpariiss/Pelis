package com.example.pelis

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String
    ): Call<MovieSearchResult>

    @GET("/")
    fun getMovieDetail(
        @Query("apikey") apiKey: String,
        @Query("i") imdbID: String
    ): Call<Movie>

    interface OmdbApi {
        @GET("/")
        fun searchMovies(
            @Query("apikey") apiKey: String,
            @Query("s") query: String
        ): Call<MovieSearchResult>

        @GET("/")
        fun getMovieDetail(
            @Query("apikey") apiKey: String,
            @Query("i") imdbID: String,
            @Query("plot") plot: String = "full"
        ): Call<Movie>
    }

}
