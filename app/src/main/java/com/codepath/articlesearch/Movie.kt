package com.codepath.articlesearch

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val MOVIE_IMG_URL = "https://image.tmdb.org/t/p/w500/"

@Keep
@Serializable
data class MovieResults(
    @SerialName("results")
    val results: List<Movie>?
)

@Keep
@Serializable
data class Movie (
    @SerialName("original_title")
    val title: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("release_date")
    val release_date: String?,
    @SerialName("original_language")
    val original_language: String?,
    @SerialName("vote_average")
    val vote_average: String?,
    @SerialName("poster_path")
    val poster_path: String?
): java.io.Serializable {
    val mediaImageUrl = "${MOVIE_IMG_URL}${poster_path}"
}
