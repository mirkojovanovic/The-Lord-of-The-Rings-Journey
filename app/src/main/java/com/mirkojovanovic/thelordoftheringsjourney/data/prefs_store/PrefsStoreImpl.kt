package com.mirkojovanovic.thelordoftheringsjourney.data.prefs_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mirkojovanovic.thelordoftheringsjourney.domain.prefs_store.PrefsStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val STORE_NAME = "settings"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)


class PrefsStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PrefsStore {

    private val dataStore = context.dataStore

    override fun getUserName(): Flow<String?> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }.map {
                it[PreferencesKeys.USER_NAME_KEY] ?: ""
            }

    override suspend fun setUserName(name: String) {
        dataStore.edit {
            it[PreferencesKeys.USER_NAME_KEY] = name
        }
    }
}

private object PreferencesKeys {
    val USER_NAME_KEY = stringPreferencesKey("user_name")
}