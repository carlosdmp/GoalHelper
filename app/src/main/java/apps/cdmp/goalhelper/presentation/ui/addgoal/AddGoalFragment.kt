package apps.cdmp.goalhelper.presentation.ui.addgoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import apps.cdmp.goalHelper.databinding.AddGoalFragmentBinding
import apps.cdmp.goalhelper.presentation.ui.main.MainButtonLogo
import apps.cdmp.goalhelper.presentation.ui.onTextChanged
import apps.cdmp.goalhelper.presentation.ui.main.MainHosted
import apps.cdmp.goalhelper.presentation.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AddGoalFragment : Fragment(), MainHosted {

    override fun onFabClick() {
        viewModel.addGoal()
    }

    private lateinit var binding: AddGoalFragmentBinding
    private val calendar by lazy { Calendar.getInstance() }

    private val viewModel: AddGoalViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddGoalFragmentBinding.inflate(inflater, container, false)
        binding.etName.onTextChanged { viewModel.updateName(it) }
        binding.tpDeadline.minDate = calendar.timeInMillis

//        binding.tpDeadline.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
//            viewModel.setDate(calendar.time)
//        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveForm.observe(this, Observer {
            binding.form = it
            if(it.done){
                binding.root.findNavController().navigateUp()
            }
        })
        viewModel.formValidation.observe(this, Observer {
            binding.errors = it
        })
        mainViewModel.showFab(MainButtonLogo.DONE)
    }

}
