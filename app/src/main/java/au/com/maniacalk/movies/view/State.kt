package au.com.maniacalk.movies.view

import au.com.maniacalk.movies.repository.model.MovieDetail
import au.com.maniacalk.movies.repository.model.SearchResults

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

open class ViewState<T>(val status: Status, val data: T? = null, val message: String? = null)

class ListViewState(status: Status, data: SearchResults? = null, message: String? = null): ViewState<SearchResults>(status, data, message)

class DetailViewState(status: Status, data: MovieDetail? = null, message: String? = null): ViewState<MovieDetail>(status, data, message)

