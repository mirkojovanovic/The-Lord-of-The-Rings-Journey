package com.mirkojovanovic.thelordoftheringsjourney.presentation.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.showError
import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var loading: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loading = AlertDialog.Builder(requireContext())
            .setView(R.layout.popup_loading)
            .setCancelable(false)
            .create()
        loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCountNumbers()
    }

    private fun showCountNumbers() {
        handleUIEvent()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countState.collectLatest { countState ->
                    with(countState) {
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
    }

    private fun handleUIEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { e ->
                    when (e) {
                        is HomeViewModel.UIEvent.ShowSnackBar -> {
                            when (val message = e.message) {
                                is UiText.DynamicString -> {
                                    requireView().showError(message.value)
                                }
                                is UiText.StringResource -> {
                                    requireView().showError(getString(message.id))
                                }
                                else -> {}
                            }
                        }
                        is HomeViewModel.UIEvent.ShowLoadingAnimation -> {
                            loading.show()
                        }
                        is HomeViewModel.UIEvent.HideLoadingAnimation -> {
                            loading.hide()
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