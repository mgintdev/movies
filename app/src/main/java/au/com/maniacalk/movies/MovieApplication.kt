package au.com.maniacalk.movies

import android.app.Application
import au.com.maniacalk.movies.domain.MovieContentViewModel
import au.com.maniacalk.movies.repository.OmdbRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApplication : Application() {

    private lateinit var movieContentViewModel: MovieContentViewModel
    private lateinit var gson: Gson
    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var repository: OmdbRepository

    companion object {
        private lateinit var _instance: MovieApplication

        fun getInstance(): MovieApplication = _instance
    }

    fun getMovieViewModel() = movieContentViewModel

    override fun onCreate() {
        super.onCreate()

        _instance = this

        gson = GsonBuilder().setPrettyPrinting().setLenient().serializeNulls().create()

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        repository = OmdbRepository(retrofit)

        movieContentViewModel = MovieContentViewModel(repository)

    }

}