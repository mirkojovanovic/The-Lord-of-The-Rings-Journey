package com.mirkojovanovic.thelordoftheringsjourney.presentation.main

import android.animation.ObjectAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.PreferenceCache
import com.mirkojovanovic.thelordoftheringsjourney.common.autoHideKeyboard
import com.mirkojovanovic.thelordoftheringsjourney.common.showError
import com.mirkojovanovic.thelordoftheringsjourney.databinding.ActivityMainBinding
import com.mirkojovanovic.thelordoftheringsjourney.presentation.home.NameListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NameListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkRequest: NetworkRequest
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    @Inject
    lateinit var prefs: PreferenceCache

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showNetworkAvailabilityMessages(view)
        setUpNavigation()
        setNavigationItemSelectedListener()
        onDestinationChange()
        setOnSplashExitAnimation()
        supportActionBar?.elevation = 0f
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun showNetworkAvailabilityMessages(view: View) {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkRequest = NetworkRequest.Builder().build()
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                view.showError(getString(R.string.network_available_message))
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                view.showError(getString(R.string.network_lost_message))
            }

            override fun onUnavailable() {
                super.onUnavailable()
                view.showError(getString(R.string.network_unavailable_message))

            }
        }
        connectivityManager.registerNetworkCallback(
            networkRequest, networkCallback
        )
    }

    private fun onDestinationChange() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.movieInfoFragment -> {
                    binding.toolbar.setNavigationOnClickListener {
                        onBackPressed()
                    }
                    binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
                }
                else -> {
                    binding.toolbar.setNavigationOnClickListener {
                        binding.root.open()
                    }
                    binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
                }
            }
        }
    }

    private fun setNavigationItemSelectedListener() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            navController.popBackStack()
            val destination = when (menuItem.itemId) {
                R.id.home -> R.id.homeFragment
                R.id.movies -> R.id.moviesFragment
                else -> -1
            }
            navController.navigate(destination)
            binding.root.close()
            true
        }
    }

    private fun setUpNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        navGraph.startDestination =
            if (prefs.userName.isNullOrBlank()) R.id.getNameFragment
            else R.id.homeFragment
        navController.graph = navGraph
        prefs.userName?.let { setUserNameInTheDrawer(it) }
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)
        setSupportActionBar(binding.toolbar)
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setUserNameInTheDrawer(userName: String) {
        val header = binding.navView.getHeaderView(0)
        val usernameTextView = header.findViewById<TextView>(R.id.user_name)
        usernameTextView.text = userName
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        autoHideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

}