package com.beefirst.zatsh.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.beefirst.zatsh.R
import com.beefirst.zatsh.utils.UserPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var userPreferences: UserPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val nav = Navigation.findNavController(this, R.id.mainNavigationFragment)
    setupBottomNavMenu(nav)
  }

  private fun setupBottomNavMenu(navController: NavController) {
    val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    bottomNav?.setupWithNavController(navController)
    bottomNav?.selectedItemId = R.id.categoriesFragment
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return item.onNavDestinationSelected(findNavController(R.id.mainNavigationFragment))
            || super.onOptionsItemSelected(item)
  }
}