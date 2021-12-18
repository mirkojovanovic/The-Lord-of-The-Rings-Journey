package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieDetailsBinding
import com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.MovieInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment() : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MovieInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextData()
    }

    private fun setTextData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.movie?.run {
                        binding.nameValue.text = name
                        binding.runtimeValue.text =
                            getString(R.string.runtime_in_min, runtimeInMinutes)
                        binding.budgetValue.text =
                            getString(R.string.movie_budget, budgetInMillions)
                        binding.revenueValue.text =
                            getString(R.string.movie_revenue, boxOfficeRevenueInMillions)
                        binding.scoreValue.text =
                            getString(R.string.movie_score, rottenTomatoesScore)
                        binding.nominationsValue.text =
                            getString(R.string.movie_nominations, academyAwardNominations)
                        binding.awardsValue.text =
                            getString(R.string.movie_awards, academyAwardWins)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}