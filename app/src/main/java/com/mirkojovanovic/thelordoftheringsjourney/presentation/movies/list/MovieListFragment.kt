package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.mirkojovanovic.thelordoftheringsjourney.common.dp
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.toMovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieListBinding
import com.mirkojovanovic.thelordoftheringsjourney.presentation.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private lateinit var serviceAdapter: MoviesAdapter

    private val viewModel by viewModels<MoviesViewModel>()

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
                    serviceAdapter.submitData(data.map { it.toMovieDoc() })
                }
            }
        }
    }

    private fun setMovieListSpacingValues() {
        binding.movies.addItemDecoration(
            VerticalSpaceItemDecoration(4.dp(), 8.dp(), 8.dp(), 8.dp(), 8.dp())
        )
    }

    private fun setUpMovieListAdapter() {
        serviceAdapter = MoviesAdapter {
            findNavController()
                .navigate(MovieListFragmentDirections.actionMoviesFragmentToMovieQuotesFragment(it))
        }
//            {
//                findNavController()
//                    .navigate(MovieListFragmentDirections.actionMoviesFragmentToMovieQuotesFragment(it))
//            })
        binding.movies.adapter = serviceAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}