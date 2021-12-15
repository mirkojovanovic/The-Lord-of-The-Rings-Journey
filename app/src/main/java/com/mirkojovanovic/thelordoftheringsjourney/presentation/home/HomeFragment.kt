package com.mirkojovanovic.thelordoftheringsjourney.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.showError
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCountNumbers()
    }

    private fun showCountNumbers() {
        lifecycleScope.launchWhenStarted {
            viewModel.countState.collectLatest { countState ->
                with(countState) {
                    error?.let { message ->
                        requireView().showError(message)
                    }
                    bookCount?.let { count ->
                        with(binding.bookCount) {
                            text = getString(R.string.book_count, count)
                            isVisible = true
                        }
                    }
                    movieCount?.let { count ->
                        with(binding.movieCount) {
                            text = getString(R.string.movie_count, count)
                            isVisible = true
                        }
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