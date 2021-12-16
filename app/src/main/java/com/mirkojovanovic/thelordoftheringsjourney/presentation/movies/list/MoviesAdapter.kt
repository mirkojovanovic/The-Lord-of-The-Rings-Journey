package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.databinding.MovieItemBinding
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MovieDoc

class MoviesAdapter(
//    private val showDetails: (MovieDoc) -> Unit,
    private val showQuotes: (String) -> Unit,
) :
    PagingDataAdapter<MovieDoc, MoviesAdapter.ViewHolder>(SERVICE_COMPARATOR) {

    class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: MovieDoc,
//            showDetails: (MovieDoc) -> Unit,
            showQuotes: (String) -> Unit,
        ) {
            with(binding) {
                name.text = movie.name
                score.text =
                    root.resources.getString(R.string.movie_score, movie.rottenTomatoesScore)
                runtime.text =
                    root.resources.getString(R.string.movie_runtime, movie.runtimeInMinutes)
                root.setOnClickListener { showQuotes(movie._id) }
            }
        }
    }

    companion object {
        private val SERVICE_COMPARATOR = object : DiffUtil.ItemCallback<MovieDoc>() {
            override fun areItemsTheSame(oldItem: MovieDoc, newItem: MovieDoc) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: MovieDoc, newItem: MovieDoc) =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, /*showDetails,*/ showQuotes) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}
