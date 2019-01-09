package apps.cdmp.goalhelper.ui.addgoal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import apps.cdmp.goalHelper.databinding.AddGoalFragmentBinding
import apps.cdmp.goalhelper.bindmodel.addgoal.AddGoal
import apps.cdmp.goalhelper.bindmodel.addgoal.FrequencyMeasure
import apps.cdmp.goalhelper.bindmodel.main.MainButtonLogo
import apps.cdmp.goalhelper.common.Tuple3
import apps.cdmp.goalhelper.common.onTextChanged
import apps.cdmp.goalhelper.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AddGoalFragment : Fragment() {

    var addGoal: AddGoal? = null
    lateinit var binding: AddGoalFragmentBinding
    lateinit var selectorMap: MutableMap<View, Tuple3<View, Boolean, View>>

    private val calendar by lazy { Calendar.getInstance() }
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
    val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddGoalFragmentBinding.inflate(inflater, container, false)
        binding.etName.onTextChanged { viewModel.updateName(it) }
        selectorMap = mutableMapOf(
            binding.bDeadline as View to
                    Tuple3(
                        binding.hDeadline, false, binding.tpDeadline as View
                    ),
            binding.bRoutine as View to
                    Tuple3(
                        binding.hRoutine, false, binding.lRoutine as View
                    )
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

        binding.tpDeadline.minDate = calendar.timeInMillis

//        binding.tpDeadline.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
//            viewModel.setDate(calendar.time)
//        }
        binding.spFreq.adapter = object : ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            FrequencyMeasure.values().map { it.display }) {

        }
        mainViewModel.showFab(MainButtonLogo.DONE, {})
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.newGoal.observe(this, Observer {
            addGoal = it
        })
        viewModel.errors.observe(this, Observer {
            binding.errors = it
        })
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
                        line.third.visibility = VISIBLE
                        selectorMap.keys.forEach { v ->
                            if (v != view) {
                                selectorMap[v]?.third?.visibility = GONE
                            }
                        }
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
