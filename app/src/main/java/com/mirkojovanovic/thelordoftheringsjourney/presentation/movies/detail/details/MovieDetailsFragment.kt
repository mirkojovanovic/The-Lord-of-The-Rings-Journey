package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.detail.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.movieDoc.run {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}