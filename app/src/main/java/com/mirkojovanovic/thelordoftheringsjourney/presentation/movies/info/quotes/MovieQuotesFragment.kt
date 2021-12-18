package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mirkojovanovic.thelordoftheringsjourney.common.dp
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieQuotesBinding
import com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.MovieInfoViewModel
import com.mirkojovanovic.thelordoftheringsjourney.presentation.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieQuotesFragment : Fragment() {

    private var _binding: FragmentMovieQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieQuotesAdapter: MovieQuotesAdapter

    private val viewModel by activityViewModels<MovieInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharacters()
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        setUpMovieCharacterQuoteList()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    with(state) {
                        movie?.let { movieQuotesAdapter.movie = movie }
                        characters?.let { movieQuotesAdapter.characters = it.toMutableList() }
                        quotes?.let { movieQuotesAdapter.quotes = it.toMutableList() }
                        movieQuotesAdapter.filter.filter(query)
                    }
                }
            }
        }
    }

    private fun setUpMovieCharacterQuoteList() {
        setUpMovieQuotesAdapter()
        setMovieQuotesSpacingValues()
    }

    private fun setMovieQuotesSpacingValues() {
        binding.quotes.addItemDecoration(
            VerticalSpaceItemDecoration(4.dp(), 8.dp(), 8.dp(), 8.dp(), 8.dp())
        )
    }

    private fun setUpMovieQuotesAdapter() {
        movieQuotesAdapter = MovieQuotesAdapter()
        binding.quotes.adapter = movieQuotesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}