package com.jorgesanaguaray.videogamesinformation

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.jorgesanaguaray.videogamesinformation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mAppBarMain.mToolbar)

        val drawerLayout: DrawerLayout = binding.mDrawerLayout
        val navigationView: NavigationView = binding.mNavigationView
        val navController = findNavController(R.id.mNavController)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_search, R.id.nav_category, R.id.nav_favorite), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.mNavController)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }

}