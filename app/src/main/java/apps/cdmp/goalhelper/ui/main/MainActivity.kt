package apps.cdmp.goalhelper.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import apps.cdmp.goalHelper.R
import apps.cdmp.goalHelper.databinding.MainActivityBinding
import apps.cdmp.goalhelper.bindmodel.main.MainButtonLogo
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModel()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)


        binding.navigationView.setupWithNavController(navController)

        isFabOn = false
        mainViewModel.mainHost.observe(this, Observer { host ->
            hideFab(onEnd = {
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        when (host.logo) {
                            MainButtonLogo.ADD -> R.drawable.ic_add_black_24dp
                            MainButtonLogo.DONE -> R.drawable.ic_done_black_24dp
                            MainButtonLogo.HIDDEN -> R.drawable.abc_btn_check_material
                        }
                    )
                )
                if (host.logo != MainButtonLogo.HIDDEN){
                    showFab()
                    binding.fab.setOnClickListener { host.onClick() }
                }
            })
        })
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
