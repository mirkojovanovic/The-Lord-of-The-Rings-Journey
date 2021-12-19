package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.showError
import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentMovieInfoBinding
import com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.details.MovieDetailsFragment
import com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes.InfoTab
import com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes.MovieQuotesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieInfoFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieInfoFragmentList: ArrayList<Fragment>
    private lateinit var movieInfoPagerAdapter: MovieInfoPageAdapter

    private val args by navArgs<MovieInfoFragmentArgs>()

    private val viewModel by activityViewModels<MovieInfoViewModel>()

    private lateinit var loading: AlertDialog


    override fun onStart() {
        super.onStart()
        viewModel.setStateMovie(args.movieDoc)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        loading = AlertDialog.Builder(requireContext())
            .setView(R.layout.popup_loading)
            .setCancelable(false)
            .create()
        loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initializeFragmentListAndPagerAdapter()
        binding.pager.adapter = movieInfoPagerAdapter
        setUpTabLayout()
        handleUIEvent()
        registerTabChange()
    }

    private fun registerTabChange() {
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> viewModel.setTab(InfoTab.Details)
                    1 -> viewModel.setTab(InfoTab.Quotes)
                }
            }
        })
    }

    private fun handleUIEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { e ->
                    when (e) {
                        is MovieInfoViewModel.UIEvent.ShowSnackBar -> {
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
                        is MovieInfoViewModel.UIEvent.ShowLoadingAnimation -> {
                            loading.show()
                        }
                        is MovieInfoViewModel.UIEvent.HideLoadingAnimation -> {
                            loading.hide()
                        }
                        is MovieInfoViewModel.UIEvent.AdaptFragmentOptionsMenu -> {
                            setHasOptionsMenu(true)
                        }
                    }
                }
            }
        }
    }

    private fun initializeFragmentListAndPagerAdapter() {
        movieInfoFragmentList = arrayListOf(
            MovieDetailsFragment(),
            MovieQuotesFragment()
        )
        movieInfoPagerAdapter = MovieInfoPageAdapter(
            movieInfoFragmentList,
            childFragmentManager,
            lifecycle
        )
    }

    private fun setUpTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.details_tab_text)
                1 -> getString(R.string.quotes_tab_text)
                else -> null
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpSearchView(inflater: MenuInflater, menu: Menu) {
        inflater.inflate(R.menu.search_quotes_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.run {
            isIconifiedByDefault = false
            isSubmitButtonEnabled = true
            queryHint = getString(R.string.search_hint)
            imeOptions = EditorInfo.IME_ACTION_SEARCH
        }
        searchView?.setOnQueryTextListener(this@MovieInfoFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        setUpSearchView(inflater, menu)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state.tab) {
                        InfoTab.Details -> {
                            search.isVisible = false
                            searchView?.visibility = View.INVISIBLE
                        }
                        InfoTab.Quotes -> {
                            search.isVisible = true
                            searchView?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (binding.pager.currentItem != 2)
            binding.pager.currentItem = 2
        viewModel.setQuery(query ?: "")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

