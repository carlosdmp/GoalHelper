package apps.cdmp.goalhelper.presentation.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import apps.cdmp.goalHelper.R
import apps.cdmp.goalHelper.databinding.MainActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private val fab
        get() = binding.fab

    private var isFabOn
        get() = fab.isClickable
        set(value) {
            fab.isClickable = value
        }

    private val currentFragment: Fragment?
        get() {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_fragment)
            val fragmentList = navHostFragment?.childFragmentManager?.fragments ?: listOf(null)
            return if (fragmentList.isNotEmpty()) fragmentList[0] else null
        }

    private object AnimConstants {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)


        binding.navigationView.setupWithNavController(navController)
        fab.setOnClickListener {
            currentFragment?.let { fragment ->
                when (fragment) {
                    is MainHosted -> fragment.onFabClick()
                }
            }
        }
        mainViewModel.mainHost.observe(this, Observer { host ->
            hideFab(onEnd = {
                when (host.logo) {
                    MainButtonLogo.ADD -> {
                        binding.fab.setImageDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_add_black_24dp
                            )
                        )
                        showFab()
                    }
                    MainButtonLogo.DONE -> {
                        binding.fab.setImageDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_add_black_24dp
                            )
                        )
                        showFab()
                    }
                    MainButtonLogo.HIDDEN -> binding.fab.setImageDrawable(null)
                }
            })
        })
        navController.addOnDestinationChangedListener { _, _, _ ->

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

    private fun showFab(
        animDuration: Long = AnimConstants.fabAnimationDuration,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {}
    ) {
        if (!isFabOn) {
            ObjectAnimator.ofFloat(
                fab,
                "translationY",
                AnimConstants.fabTranslation.second,
                AnimConstants.fabTranslation.first
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
                AnimConstants.fabAlpha.second,
                AnimConstants.fabAlpha.first
            ).apply {
                duration = AnimConstants.fabAnimationDuration
                start()
            }
        } else {
            onEnd()
        }

    }

    private fun hideFab(
        animDuration: Long = AnimConstants.fabAnimationDuration,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {}
    ) {
        if (isFabOn) {
            ObjectAnimator.ofFloat(
                fab,
                "translationY",
                AnimConstants.fabTranslation.first,
                AnimConstants.fabTranslation.second
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
                AnimConstants.fabAlpha.first,
                AnimConstants.fabAlpha.second
            ).apply {
                duration = animDuration
                start()
            }
        } else {
            onEnd()
        }
    }
}
