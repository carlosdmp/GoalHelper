package apps.cdmp.goalhelper.presentation.ui.addgoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import apps.cdmp.goalHelper.databinding.AddGoalFragmentBinding
import apps.cdmp.goalhelper.common.doNothing
import apps.cdmp.goalhelper.presentation.ui.main.MainButtonLogo
import apps.cdmp.goalhelper.presentation.ui.main.MainHosted
import apps.cdmp.goalhelper.presentation.ui.main.MainViewModel
import apps.cdmp.goalhelper.presentation.ui.onTextChanged
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class AddGoalFragment : Fragment(), MainHosted {

    private val viewModel: AddGoalViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var binding: AddGoalFragmentBinding

    override fun onFabClick() {
        viewModel.addGoal()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = AddGoalFragmentBinding.inflate(inflater, container, false)
        binding.etName.onTextChanged { viewModel.updateName(it) }
        initView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveForm.observe(this, Observer {
            binding.form = it
            if (it.done) {
                binding.root.findNavController().navigateUp()
            }
        })
        viewModel.formValidation.observe(this, Observer {
            binding.errors = it
        })
        mainViewModel.showFab(MainButtonLogo.DONE)
    }

    private fun initView() {
        val minDate: Date = Calendar.getInstance().time
        binding.tpDeadline.init(
                minDate.year,
                minDate.month,
                minDate.day
        ) { _, year, monthOfYear, dayOfMonth ->
            val selected = GregorianCalendar.getInstance()
            selected.set(year, monthOfYear, dayOfMonth)
            viewModel.updateDate(selected.time)
        }
        binding.tpDeadline.minDate = minDate.time
    }
}
