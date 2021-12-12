package com.mirkojovanovic.thelordoftheringsjourney

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.mirkojovanovic.thelordoftheringsjourney.core.kotlin.hideSystemUI

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnSplashExitAnimation()
        hideSystemUI()
    }

    private fun setOnSplashExitAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // custom exit on splashScreen
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // custom animation.
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ALPHA,
                    1f,
                    0f
                ).apply {
                    duration = 1000
                    // Call SplashScreenView.remove at the end of your custom animation.
                    doOnEnd {
                        splashScreenView.remove()
                    }
                }.also {
                    // Run your animation.
                    it.start()
                }
            }
        }
    }
}