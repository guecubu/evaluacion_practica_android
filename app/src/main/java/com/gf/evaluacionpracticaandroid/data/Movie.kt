package com.gf.evaluacionpracticaandroid.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
        @SerializedName("imdbID") val id: String,
        @SerializedName("Title") val title: String,
        @SerializedName("Year") val year: String? = null,
        @SerializedName("Poster") val thumbnail: String? = null,
        @SerializedName("Type") val type: String? = null,
        // Detail
        @SerializedName("Runtime") val runtime: String? = null,
        @SerializedName("Genre") val genre: String? = null,
        @SerializedName("Director") val director: String? = null,
        @SerializedName("Writer") val writer: String? = null,
        @SerializedName("Actors") val actors: String? = null,
        @SerializedName("Plot") val plot: String? = null,
        @SerializedName("Language") val language: String? = null,
        @SerializedName("Country") val country: String? = null,
                ) : Serializable

data class MovieSearchResponse(
        @SerializedName("Search") val search: List<Movie>? = null,
        @SerializedName("totalResults") val totalResults: String? = null,
        @SerializedName("Response") val response: String
                              ) : Serializable
