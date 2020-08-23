package au.com.maniacalk.movies.repository.model

import com.google.gson.annotations.SerializedName

data class SearchResults(
    @SerializedName("Response")
    val response: String? = null,
    @SerializedName("Error")
    val error: String? = null,
    @SerializedName("Search")
    val movies: List<Movie> = emptyList(),
    val totalResults: String? = null
) {
    data class Movie(
        val imdbID: String,
        @SerializedName("Poster")
        val poster: String? = null,
        @SerializedName("Title")
        val title: String,
        @SerializedName("Type")
        val type: String,
        @SerializedName("Year")
        val year: String
    )
}