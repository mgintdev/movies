package au.com.maniacalk.movies.repository

import au.com.maniacalk.movies.BuildConfig
import au.com.maniacalk.movies.repository.model.MovieDetail
import au.com.maniacalk.movies.repository.model.SearchResults
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class OmdbRepository(retrofit: Retrofit) {

    private var service: ApiService

    init {
        service = retrofit.create(ApiService::class.java)
    }

    interface ApiService {
        @GET("/")
        suspend fun searchMovies(
            @Query("apikey") apiKey: String,
            @Query("type") type: String = "movie",
            @Query("s") query: String
        ): SearchResults

        @GET("/")
        suspend fun getDetail(
            @Query("apikey") apiKey: String,
            @Query("i") id: String
        ): MovieDetail

    }

    suspend fun searchMovies(filter: String? = null): SearchResults =
        service.searchMovies(query = filter ?: "the mat", apiKey = BuildConfig.omdbApiKey)

    suspend fun getDetail(id: String): MovieDetail =
        service.getDetail(id = id, apiKey = BuildConfig.omdbApiKey)
}