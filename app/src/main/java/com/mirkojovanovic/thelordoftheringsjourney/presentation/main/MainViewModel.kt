package com.mirkojovanovic.thelordoftheringsjourney.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mirkojovanovic.thelordoftheringsjourney.domain.prefs_store.PrefsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsStore: PrefsStore
): ViewModel() {

    fun getUserName() =
        prefsStore.getUserName().asLiveData().value
}