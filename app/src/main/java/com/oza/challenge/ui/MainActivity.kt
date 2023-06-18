package com.oza.challenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.oza.challenge.R
import com.oza.challenge.model.ImageData
import com.oza.challenge.ui.details.ImageDetailFragment
import com.oza.challenge.ui.home.HomeFragment
import com.oza.challenge.ui.home.ItemClickListener
import com.oza.challenge.ui.login.LoginFragment
import com.oza.challenge.ui.register.RegistrationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Get the NavController
        navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}