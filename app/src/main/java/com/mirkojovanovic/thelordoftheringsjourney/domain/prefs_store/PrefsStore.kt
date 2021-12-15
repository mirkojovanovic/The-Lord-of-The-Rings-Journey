package com.mirkojovanovic.thelordoftheringsjourney.domain.prefs_store

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun getUserName(): Flow<String?>
    suspend fun setUserName(name: String)
}
