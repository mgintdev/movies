package au.com.maniacalk.movies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import au.com.maniacalk.movies.BuildConfig
import au.com.maniacalk.movies.R
import au.com.maniacalk.movies.repository.model.SearchResults
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListAdapter(private val movies: MutableList<SearchResults.Movie>, val onItemClickListener: (SearchResults.Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            const val N_A = "N/A"
        }

        fun update(
            movie: SearchResults.Movie,
            onItemClickListener: (SearchResults.Movie) -> Unit
        ) {
            itemView.apply {
                tvTitle.text = movie.title

                movie.poster?.let {
                        Picasso.get()
                            .apply { setIndicatorsEnabled(BuildConfig.DEBUG) }
                            .load(it)
                            .error(android.R.drawable.ic_menu_camera)
                            .into(ivPoster)
                }
                setOnClickListener {
                    onItemClickListener(movie)
                }
                tvYear.text = movie.year
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        )

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.update(movies[position], onItemClickListener)
    }

    fun updateData(movies: List<SearchResults.Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
    }
}