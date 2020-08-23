package au.com.maniacalk.movies.repository.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("Response")
    val response: String? = null,
    @SerializedName("Error")
    val error: String? = null,
    @SerializedName("Actors")
    val actors: String? = null,
    @SerializedName("Awards")
    val awards: String? = null,
    @SerializedName("BoxOffice")
    val boxOffice: String? = null,
    @SerializedName("Country")
    val country: String? = null,
    @SerializedName("DVD")
    val dVD: String? = null,
    @SerializedName("Director")
    val director: String? = null,
    @SerializedName("Genre")
    val genre: String? = null,
    val imdbID: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    @SerializedName("Language")
    val language: String? = null,
    @SerializedName("Metascore")
    val metascore: String? = null,
    @SerializedName("Plot")
    val plot: String? = null,
    @SerializedName("Poster")
    val poster: String? = null,
    @SerializedName("Production")
    val production: String? = null,
    @SerializedName("Rated")
    val rated: String? = null,
    @SerializedName("Ratings")
    val ratings: List<Rating?>? = null,
    @SerializedName("Released")
    val released: String? = null,
    @SerializedName("Runtime")
    val runtime: String? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Type")
    val type: String? = null,
    @SerializedName("Website")
    val website: String? = null,
    @SerializedName("Writer")
    val writer: String? = null,
    @SerializedName("Year")
    val year: String? = null
) {
    data class Rating(
        @SerializedName("Source")
        val source: String? = null,
        @SerializedName("Value")
        val value: String? = null
    )
}