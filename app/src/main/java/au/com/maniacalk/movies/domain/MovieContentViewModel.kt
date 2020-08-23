package au.com.maniacalk.movies.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import au.com.maniacalk.movies.repository.OmdbRepository
import au.com.maniacalk.movies.view.DetailViewState
import au.com.maniacalk.movies.view.ListViewState
import au.com.maniacalk.movies.view.Status
import kotlinx.coroutines.Dispatchers

class MovieContentViewModel(private val repo: OmdbRepository) : ViewModel() {

    fun searchMovies(filter: String? = null): LiveData<ListViewState> = liveData(Dispatchers.IO) {
        emit(ListViewState(Status.LOADING))
        try {
            emit(ListViewState(Status.SUCCESS, repo.searchMovies(filter)))
        } catch (e: Exception) {
            emit(ListViewState(Status.ERROR, message = e.message))
        }
    }

    fun getDetail(id: String): LiveData<DetailViewState> = liveData(Dispatchers.IO) {
        emit(DetailViewState(Status.LOADING))
        try {
            emit(DetailViewState(Status.SUCCESS, repo.getDetail(id)))
        } catch (e: Exception) {
            emit(DetailViewState(Status.ERROR, message = e.message))
        }
    }
}