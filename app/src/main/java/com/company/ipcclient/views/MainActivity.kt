

package com.company.ipcclient.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.company.ipcclient.R
import com.company.ipcclient.service.IPCService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mNavigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setting up navigation graph
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavigationController = navHostFragment.navController
        // Setting up bottom navigation
        setUpBottomNav(mNavigationController)
        NavigationUI.setupActionBarWithNavController(this, mNavigationController)
        // Starting IPC Service
        val intent = Intent(this, IPCService::class.java)
        startService(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavigationController.navigateUp()
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, mNavigationController)
        return super.onOptionsItemSelected(item)
    }
}