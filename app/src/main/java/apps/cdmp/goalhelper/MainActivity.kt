package apps.cdmp.goalhelper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import apps.cdmp.goalHelper.R
import apps.cdmp.goalHelper.databinding.MainActivityBinding
import apps.cdmp.goalhelper.ui.MainFragment


class MainActivity : AppCompatActivity() {

    private val fab
        get() = binding.fab

    private var isFabOn
        get() = fab.isClickable
        set(value) {
            fab.isClickable = value
        }

    private object Constants {
        val fabTranslation = 0f to 200f
        val fabAlpha = 1f to 0f
        const val fabAnimationDuration = 500L
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<MainActivityBinding>(
            this,
            R.layout.main_activity
        )
    }
    private val drawerLayout by lazy { binding.drawerLayout }
    private val navController by lazy { Navigation.findNavController(this, R.id.main_nav_fragment) }
    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph, drawerLayout) }

    private val onFabAction = {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_fragment)
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        when (fragment) {
            is MainFragment -> fragment.onFabClicked()
            else -> {
            }
        }
    }

    private val onNavigate: (destination: NavDestination) -> Unit = { destination ->
        when (destination.id) {
            R.id.summary_fragment -> {
                hideFab(onEnd = {
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_black_24dp))
                    showFab()
                })

            }
            R.id.addgoal_fragment -> {
                hideFab(onEnd = {
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_done_black_24dp))
                    showFab()
                })
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
        binding.fab.setOnClickListener { onFabAction() }
        navController.addOnDestinationChangedListener { _, destination, _ -> onNavigate(destination) }
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

    private fun showFab(
        animDuration: Long = Constants.fabAnimationDuration,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {}
    ) {
        if (!isFabOn) {
            ObjectAnimator.ofFloat(
                fab,
                "translationY",
                Constants.fabTranslation.second,
                Constants.fabTranslation.first
            ).apply {
                duration = animDuration
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        onStart()
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        isFabOn = true
                        onEnd()
                    }
                })
                start()
            }
            ObjectAnimator.ofFloat(
                fab, "alpha",
                Constants.fabAlpha.second,
                Constants.fabAlpha.first
            ).apply {
                duration = Constants.fabAnimationDuration
                start()
            }
        } else {
            onEnd()
        }

    }

    private fun hideFab(
        animDuration: Long = Constants.fabAnimationDuration,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {}
    ) {
        if (isFabOn) {
            ObjectAnimator.ofFloat(
                fab,
                "translationY",
                Constants.fabTranslation.first,
                Constants.fabTranslation.second
            ).apply {
                duration = animDuration
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        isFabOn = false
                        onStart()
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        onEnd()
                    }
                })
                start()
            }
            ObjectAnimator.ofFloat(
                fab,
                "alpha",
                Constants.fabAlpha.first,
                Constants.fabAlpha.second
            ).apply {
                duration = animDuration
                start()
            }
        } else {
            onEnd()
        }
    }
}
