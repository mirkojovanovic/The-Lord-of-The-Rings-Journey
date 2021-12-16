package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mirkojovanovic.thelordoftheringsjourney.common.dp
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieQuotesBinding
import com.mirkojovanovic.thelordoftheringsjourney.presentation.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieQuotesFragment : Fragment() {

    private var _binding: FragmentMovieQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieQuotesAdapter: MovieQuotesAdapter

    private val viewModel by viewModels<MovieQuotesViewModel>()
    private val args by navArgs<MovieQuotesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMovieQuotesList()
        viewModel.loadMovieQuotes(args.movieId)
    }

    private fun setUpMovieQuotesList() {
        setUpMovieQuotesAdapter()
        setMovieQuotesSpacingValues()
        submitListData()
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

    private fun submitListData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieQuotes.collectLatest { data ->
                data?.let {
                    movieQuotesAdapter.submitData(data)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}