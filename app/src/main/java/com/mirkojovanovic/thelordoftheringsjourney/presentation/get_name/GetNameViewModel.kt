package com.mirkojovanovic.thelordoftheringsjourney.presentation.get_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirkojovanovic.thelordoftheringsjourney.domain.prefs_store.PrefsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetNameViewModel @Inject constructor(
    private val prefsStore: PrefsStore
) : ViewModel() {

    fun setUserName(name: String) {
        viewModelScope.launch {
            prefsStore.setUserName(name)
        }
    }
}