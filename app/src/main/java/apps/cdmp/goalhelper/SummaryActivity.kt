package apps.cdmp.goalhelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import apps.cdmp.goalHelper.R
import apps.cdmp.goalHelper.databinding.SummaryActivityBinding

class SummaryActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<SummaryActivityBinding>(
            this,
            R.layout.summary_activity
        )
    }
    private val drawerLayout by lazy { binding.drawerLayout }
    private val navController by lazy { Navigation.findNavController(this, R.id.summary_nav_fragment) }
    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph, drawerLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        binding.navigationView.setupWithNavController(navController)
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
