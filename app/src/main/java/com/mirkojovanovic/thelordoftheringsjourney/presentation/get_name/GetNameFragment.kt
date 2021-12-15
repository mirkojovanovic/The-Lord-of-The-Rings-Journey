package com.mirkojovanovic.thelordoftheringsjourney.presentation.get_name

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.focusAndShowKeyboard
import com.mirkojovanovic.thelordoftheringsjourney.common.onDone
import com.mirkojovanovic.thelordoftheringsjourney.common.setAsRootView
import com.mirkojovanovic.thelordoftheringsjourney.databinding.FragmentGetNameBinding
import com.mirkojovanovic.thelordoftheringsjourney.presentation.home.NameListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class GetNameFragment : Fragment() {

    private var _binding: FragmentGetNameBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameListener: NameListener

    private val viewModel by viewModels<GetNameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        hideActionBar()
        _binding = FragmentGetNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun hideActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
        setupInputField()
        setOnForwardButtonClick()
    }

    private fun setOnForwardButtonClick() {
        binding.forwardButton.setOnClickListener {
            binding.nameInput.setUserNameOrShowError()
        }
    }

    private fun setupInputField() {
        binding.nameInput.run {
            setAsRootView()
            focusAndShowKeyboard()
            onDone { setUserNameOrShowError() }
        }
    }

    private fun EditText.setUserNameOrShowError() {
        if (text.toString().isNotBlank()) {
            saveUserName()
            clearBackStackAndGoToHome()
        } else {
            error = "Name cannot be empty"
        }
    }

    private fun clearBackStackAndGoToHome() {
        findNavController().popBackStack()
        findNavController().navigate(R.id.homeFragment)
    }


    private fun saveUserName() {
        val name = binding.nameInput.text.toString()
        nameListener.setUserNameInTheDrawer(name)
        viewModel.setUserName(name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showActionBar()
    }

    private fun showActionBar() {
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            nameListener = context as NameListener
        } catch (e: ClassCastException) {
            Timber.d("Main activity does not implement NameListener")
        }
    }
}