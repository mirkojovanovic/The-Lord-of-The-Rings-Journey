package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.detail.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieQuotesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieQuotesFragment : Fragment() {

    private var _binding: FragmentMovieQuotesBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<MovieQuotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}