package com.mirkojovanovic.thelordoftheringsjourney.common


import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

class PreferenceCache @Inject constructor(
    context: Context
) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)


    var isRan: Boolean
        get() = prefs.getBoolean(IS_RAN, false)
        set(value) = with(prefs.edit()) {
            putBoolean(IS_RAN, value)
            apply()
        }

    var userName: String?
        get() = prefs.getString(USERNAME, null)
        set(value) = with(prefs.edit()) {
            putString(USERNAME, value)
            apply()
        }


    fun clearSession() {
        prefs.edit()
            .remove(IS_RAN)
            .apply()
    }

    companion object {
        private const val IS_RAN = "is_ran"
        private const val USERNAME = "username"
    }
}