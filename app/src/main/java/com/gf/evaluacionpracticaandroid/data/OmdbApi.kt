package com.gf.evaluacionpracticaandroid.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    suspend fun getMoviesBySearch(
            @Query("s") search: String,
            @Query("apikey") apiKey: String = "f75108b"
                                 ): MovieSearchResponse

    @GET("/")
    suspend fun getMovieDetails(
            @Query("i") id: String,
            @Query("apikey") apiKey: String = "f75108b",
            @Query("plot") plot: String = "full"

                               ): Movie

    companion object {
        fun getInstance(): OmdbApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.omdbapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(OmdbApi::class.java)
        }
    }
}
