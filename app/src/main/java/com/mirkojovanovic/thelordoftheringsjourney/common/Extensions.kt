package com.mirkojovanovic.thelordoftheringsjourney.common

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.google.android.material.snackbar.Snackbar
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.Constants.BASE_URL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.Request
import java.io.IOException
import kotlin.math.roundToInt

/**
 * Hides the system bars and makes the Activity "fullscreen". If this should be the default
 * state it should be called from [Activity.onWindowFocusChanged] if hasFocus is true.
 * It is also recommended to take care of cutout areas. The default behavior is that the app shows
 * in the cutout area in portrait mode if not in fullscreen mode. This can cause "jumping" if the
 * user swipes a system bar to show it. It is recommended to set [WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER],
 * call [showBelowCutout] from [Activity.onCreate]
 * (see [Android Developers article about cutouts](https://developer.android.com/guide/topics/display-cutout#never_render_content_in_the_display_cutout_area)).
 * @see showSystemUI
 * @see addSystemUIVisibilityListener
 */
fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let {
            // Default behavior is that if navigation bar is hidden, the system will "steal" touches
            // and show it again upon user's touch. We just want the user to be able to show the
            // navigation bar by swipe, touches are handled by custom code -> change system bar behavior.
            // Alternative to deprecated SYSTEM_UI_FLAG_IMMERSIVE.
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // make navigation bar translucent (alternative to deprecated
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            // - do this already in hideSystemUI() so that the bar
            // is translucent if user swipes it up
            window.navigationBarColor = getColor(R.color.internal_black_semitransparent_light)
            // Finally, hide the system bars, alternative to View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            // and SYSTEM_UI_FLAG_FULLSCREEN.
            it.hide(WindowInsets.Type.systemBars())
        }
    } else {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                // Do not let system steal touches for showing the navigation bar
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        // Keep the app content behind the bars even if user swipes them up
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        // make navbar translucent - do this already in hideSystemUI() so that the bar
        // is translucent if user swipes it up
        @Suppress("DEPRECATION")
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }
}


/**
 * Shows the system bars and returns back from fullscreen.
 * @see hideSystemUI
 * @see addSystemUIVisibilityListener
 */
fun Activity.showSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // show app content in fullscreen, i. e. behind the bars when they are shown (alternative to
        // deprecated View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.setDecorFitsSystemWindows(false)
        // finally, show the system bars
        window.insetsController?.show(WindowInsets.Type.systemBars())
    } else {
        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}

fun Request.signWithToken(accessToken: String?) =
    if (this.url.toString().contains(BASE_URL) && !accessToken.isNullOrEmpty()) {
        newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    } else this

fun View.showError(message: String?) {
    val defaultError = context?.getString(R.string.default_error)
    Snackbar.make(
        this,
        if (message.isNullOrEmpty() && !defaultError.isNullOrEmpty())
            defaultError
        else "",
        Snackbar.LENGTH_SHORT
    ).show()
}

suspend fun <T> Flow<Resource<T>>.collectLatestWithLoading(
    context: Context,
    action: suspend (value: Resource<T>) -> Unit,
) {
    val loading = AlertDialog.Builder(context)
        .setView(R.layout.popup_loading)
        .setCancelable(false)
        .create()
    loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    collectLatest {
        if (it is Resource.Loading) loading.show()
        else loading.cancel()
        action(it)
    }
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.setAsRootView() {
    isFocusable = true
    isFocusableInTouchMode = true
    isClickable = true

    onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) v.hideKeyboard()
    }
}

fun View.focusAndShowKeyboard() {
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    requestFocus()
    if (hasWindowFocus()) {
        showTheKeyboardNow()
    } else {
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    if (hasFocus) {
                        this@focusAndShowKeyboard.showTheKeyboardNow()
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            })
    }
}

fun EditText.onDone(callback: (EditText) -> Unit) {
    imeOptions = EditorInfo.IME_ACTION_DONE
    maxLines = 1
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard()
            callback.invoke(this)
            true
        } else false
    }
}

/*
fun <T> Context.getDataStoreFlow(key: Preferences.Key<T>, defaultValue: T?) =
    dataStore.data.catch { exception ->
        if (exception is IOException)
            emit(emptyPreferences())
        else throw exception
    }.map {
        it[key] ?: defaultValue
    }
*/


/*
fun <T> Context.getDataStoreLiveData(key: Preferences.Key<T>, defaultValue: T?) =
    getDataStoreFlow(key, defaultValue).asLiveData()
*/

/*
suspend fun <T> Context.setDataStoreValue(key: Preferences.Key<T>, value: T) = dataStore.edit {
    it[key] = value
}
*/

fun Int.dp(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return px.roundToInt()
}
