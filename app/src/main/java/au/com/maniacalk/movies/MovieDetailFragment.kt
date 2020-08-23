package au.com.maniacalk.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import au.com.maniacalk.movies.MovieListFragment.Companion.ID
import au.com.maniacalk.movies.repository.model.MovieDetail
import au.com.maniacalk.movies.view.Status
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.android.synthetic.main.movie_detail_line.view.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        arguments?.getString(ID)?.let { id ->
            MovieApplication.getInstance()
                .getMovieViewModel()
                .getDetail(id)
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

    }

    private fun showError(message: String? = null) {
        progress.hide()
    }

    private fun showLoading(data: MovieDetail?) {
        progress.show()
    }

    private fun showResults(movie: MovieDetail?) {
        progress.hide()
        Timber.d("Results")

        movie?.let {
            tvTitle.text = movie.title
            movie.poster?.let {
                Picasso.get()
                    .apply { setIndicatorsEnabled(BuildConfig.DEBUG) }
                    .load(it)
                    .error(android.R.drawable.ic_menu_camera)
                    .into(ivPoster)
            }

            metaDataContainer.removeAllViews()

            metaDataContainer.addView(
                (layoutInflater.inflate(R.layout.movie_detail_line, metaDataContainer, false) as View).apply {
                    this.tvLabel.text = "Type"
                    this.tvValue.text = movie.type
                }
            )

            metaDataContainer.addView(
                (layoutInflater.inflate(R.layout.movie_detail_line, metaDataContainer, false) as View).apply {
                    tvLabel.text = "Plot"
                    tvValue.text = movie.plot
                }
            )
            metaDataContainer.addView(
                (layoutInflater.inflate(R.layout.movie_detail_line, metaDataContainer, false) as View).apply {
                    tvLabel.text = "Languages"
                    tvValue.text = movie.language
                }
            )
            metaDataContainer.addView(
                (layoutInflater.inflate(R.layout.movie_detail_line, metaDataContainer, false) as View).apply {
                    tvLabel.text = "Actors"
                    tvValue.text = movie.actors
                }
            )

            metaDataContainer.addView(
                layoutInflater.inflate(R.layout.movie_detail_line, metaDataContainer, false).apply {
                    tvLabel.text = "Duration"
                    tvValue.text = movie.runtime
                }
            )





        }


    }

}
