package apps.cdmp.goalhelper.ui.addgoal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import apps.cdmp.goalHelper.databinding.AddGoalFragmentBinding
import apps.cdmp.goalhelper.common.Tuple
import apps.cdmp.goalhelper.common.and
import apps.cdmp.goalhelper.common.onTextChanged
import org.koin.android.viewmodel.ext.android.viewModel

class AddGoalFragment : Fragment() {

    lateinit var binding: AddGoalFragmentBinding
    lateinit var selectorMap: MutableMap<View, Tuple<View, Boolean>>

    var isFabVisible = false

    private object Constants {
        val fabTranslation = 0f to 200f
        val fabAlpha = 1f to 0f
        const val fabAnimationDuration = 500L
    }

    companion object {
        fun newInstance() = AddGoalFragment()
    }

    private val viewModel: AddGoalViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddGoalFragmentBinding.inflate(inflater, container, false)
        binding.fabDone.setOnClickListener { hideFab() }
        binding.etName.onTextChanged { viewModel.updateName(it) }
        selectorMap = mutableMapOf(
            binding.bDeadline to (binding.hDeadline and false), binding.bRoutine to (binding.hRoutine and false)
        )
        expandLine(binding.bDeadline)
        listOf(
            binding.bDeadline,
            binding.bRoutine
        ).forEach { button ->
            button.setOnClickListener {
                expandLine(button)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.fabDone.translationY = Constants.fabTranslation.second
        binding.fabDone.alpha = Constants.fabAlpha.second
        viewModel.newGoal.observe(this, Observer {
            binding.etName.error = it.name.error
            if (it.name.error == null) {
                showFab()
            } else {
                hideFab()
            }
        })
    }

    private fun showFab() {
        if (!isFabVisible) {
            ObjectAnimator.ofFloat(
                binding.fabDone,
                "translationY",
                Constants.fabTranslation.second,
                Constants.fabTranslation.first
            ).apply {
                duration = Constants.fabAnimationDuration
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.fabDone.isClickable = true
                        binding.fabDone.isFocusable = true
                        isFabVisible = true
                    }
                })
                start()
            }
            ObjectAnimator.ofFloat(
                binding.fabDone, "alpha",
                Constants.fabAlpha.second,
                Constants.fabAlpha.first
            ).apply {
                duration = Constants.fabAnimationDuration
                start()
            }
        }

    }

    private fun hideFab() {
        if (isFabVisible) {
            ObjectAnimator.ofFloat(
                binding.fabDone,
                "translationY",
                Constants.fabTranslation.first,
                Constants.fabTranslation.second
            ).apply {
                duration = Constants.fabAnimationDuration
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        binding.fabDone.isClickable = false
                        binding.fabDone.isFocusable = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        isFabVisible = false
                    }
                })
                start()
            }
            ObjectAnimator.ofFloat(
                binding.fabDone,
                "alpha",
                Constants.fabAlpha.first,
                Constants.fabAlpha.second
            ).apply {
                duration = Constants.fabAnimationDuration
                start()
            }
        }
    }

    private fun expandLine(view: View) {
        val line = selectorMap[view]
        if (line != null && !line.second) {
            ObjectAnimator.ofFloat(
                line.first,
                "scaleX",
                0f,
                1f
            ).apply {
                duration = Constants.fabAnimationDuration
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        line.second = true
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        selectorMap.keys.forEach { v ->
                            if (v != view) {
                                hideLine(v)
                            }
                        }
                    }
                })
                start()
            }
            ObjectAnimator.ofFloat(
                line.first, "alpha",
                Constants.fabAlpha.second,
                Constants.fabAlpha.first
            ).apply {
                duration = Constants.fabAnimationDuration
                start()
            }
        }
    }

    private fun hideLine(view: View) {
        val line = selectorMap[view]
        if (line != null && line.second) {
            ObjectAnimator.ofFloat(
                line.first,
                "scaleX",
                1f,
                0f
            ).apply {
                duration = Constants.fabAnimationDuration
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        line.second = false
                    }
                })
                start()
            }
            ObjectAnimator.ofFloat(
                line.first, "alpha",
                Constants.fabAlpha.first,
                Constants.fabAlpha.second
            ).apply {
                duration = Constants.fabAnimationDuration
                start()
            }
        }
    }

}
