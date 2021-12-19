package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.dp
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.toMovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieListBinding
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies.GetMoviesPageUseCase
import com.mirkojovanovic.thelordoftheringsjourney.presentation.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter

    private val viewModel by viewModels<MovieListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMovieList()
        setHasOptionsMenu(true)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sortType.collectLatest {
                viewModel.loadMovies()
                binding.movies.scrollToPosition(0)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movies_sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when (item.itemId) {
            R.id.unsorted -> {
                viewModel.setSortType(GetMoviesPageUseCase.SortType.Unsorted)
                viewModel.setSortBy(null)
                return true
            }
            R.id.ascending -> {
                viewModel.setSortType(GetMoviesPageUseCase.SortType.Ascending)
                return true
            }
            R.id.descending -> {
                viewModel.setSortType(GetMoviesPageUseCase.SortType.Descending)
                return true
            }
            R.id.runtime -> {
                viewModel.setSortBy(GetMoviesPageUseCase.SortBy.Runtime)
                return true
            }
            R.id.score -> {
                viewModel.setSortBy(GetMoviesPageUseCase.SortBy.Score)
                return true
            }
        }
        return false
    }

    private fun setUpMovieList() {
        setUpMovieListAdapter()
        setMovieListSpacingValues()
        submitListData()
    }

    private fun submitListData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest { data ->
                data?.let {
                    moviesAdapter.submitData(data.map { it.toMovieDoc() })
                }
            }
        }
    }

    private fun setMovieListSpacingValues() {
        binding.movies.addItemDecoration(
            VerticalSpaceItemDecoration(8.dp(), 12.dp(), 12.dp(), 12.dp(), 12.dp())
        )
    }

    private fun setUpMovieListAdapter() {
        moviesAdapter = MoviesAdapter {
            findNavController()
                .navigate(MovieListFragmentDirections.actionMoviesFragmentToMovieInfoFragment(it))
        }
        binding.movies.adapter = moviesAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}