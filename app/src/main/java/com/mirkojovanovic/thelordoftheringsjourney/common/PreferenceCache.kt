package com.mirkojovanovic.thelordoftheringsjourney.common


import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceCache @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var userName: String?
        get() = prefs.getString(USERNAME, null)
        set(value) = with(prefs.edit()) {
            putString(USERNAME, value)
            apply()
        }


    fun clearSession() {
        prefs.edit()
            .remove(USERNAME)
            .apply()
    }

    companion object {
        private const val USERNAME = "username"
    }
}