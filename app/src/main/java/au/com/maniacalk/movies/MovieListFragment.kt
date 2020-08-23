package au.com.maniacalk.movies

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.com.maniacalk.movies.repository.model.SearchResults
import au.com.maniacalk.movies.view.MovieListAdapter
import au.com.maniacalk.movies.view.Status
import kotlinx.android.synthetic.main.movie_list_fragment.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieListFragment : Fragment(), TextWatcher, RecyclerView.OnItemTouchListener {

    private var currentFilter: String? = null
    private var currentPosition: Int = 0
    private lateinit var layoutManager: LinearLayoutManager
    private var currentData: SearchResults? = null
    private lateinit var adapter: MovieListAdapter

    companion object {
        const val ID = "id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = MovieListAdapter(mutableListOf()) {
            Timber.d(it.imdbID)
            currentPosition = layoutManager.findFirstVisibleItemPosition()
            findNavController().navigate(
                R.id.action_toMovieDetail, bundleOf(
                    ID to it.imdbID
                )
            )
        }
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(this)

        tvSearchFilter.setText("the matrix")

        tvSearchFilter.addTextChangedListener(this)
    }

    override fun onStart() {
        super.onStart()

        currentData?.let {
            showResults(currentData)
        } ?: search()
    }

    fun hideKeyboard() {
        (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(tvSearchFilter.windowToken, 0)
        recyclerView.requestFocus()
    }

    fun isKeyboardShown(): Boolean {
        return (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).isAcceptingText
    }


    fun search(filter: String? = null) {
        currentFilter = filter

        if (currentFilter != filter) {
            currentPosition = 0
        }

        tvSearchFilter.setError(null)
        MovieApplication.getInstance()
            .getMovieViewModel()
            .searchMovies(filter ?: "the matrix")
            .observe(this, Observer { state ->
                state?.let { state ->
                    when (state.status) {
                        Status.LOADING -> showLoading(state.data)
                        Status.SUCCESS -> {
                            state.data?.error?.let {
                                showError(it)
                            } ?: showResults(state.data)
                        }
                        Status.ERROR -> showError()
                    }
                }
            })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("POS", layoutManager.findFirstVisibleItemPosition())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val lastPosition = savedInstanceState?.getInt("POS") ?: 0
        recyclerView.scrollToPosition(lastPosition)
    }

    private fun showLoading(data: Any?) {
        progress.show()
    }

    private fun showResults(data: SearchResults?) {
        progress.hide()
        currentData = data
        Timber.d("Results")
        adapter.updateData(data?.movies.orEmpty())
        adapter.notifyDataSetChanged()
hideKeyboard()
        recyclerView.scrollToPosition(currentPosition)
    }

    private fun showError(message: String? = null) {
        progress.hide()
        tvSearchFilter.setError("Invalid Query")
    }

    var handler: Handler = Handler()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun afterTextChanged(s: Editable?) {
        s?.let {
            if (it.isNotBlank() && it.length > 2) {

                handler.removeCallbacksAndMessages("FILTER")
                handler.postDelayed({
                    search(it.toString())
                }, "FILTER", 500)
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        hideKeyboard()
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return isKeyboardShown()
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}
