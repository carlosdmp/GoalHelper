package apps.cdmp.goalhelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import apps.cdmp.goalHelper.R
import apps.cdmp.goalHelper.databinding.MainActivityBinding
import apps.cdmp.goalhelper.ui.MainFragment


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<MainActivityBinding>(
            this,
            R.layout.main_activity
        )
    }
    private val drawerLayout by lazy { binding.drawerLayout }
    private val navController by lazy { Navigation.findNavController(this, R.id.main_nav_fragment) }
    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph, drawerLayout) }

    private val fabAction = {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_fragment)
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        when (fragment) {
            is MainFragment -> fragment.onFabClicked()
            else -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        binding.navigationView.setupWithNavController(navController)
        binding.fab.setOnClickListener { fabAction() }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.summary_fragment -> {
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_black_24dp))
                }
                R.id.addgoal_fragment -> {
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_done_black_24dp))
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
