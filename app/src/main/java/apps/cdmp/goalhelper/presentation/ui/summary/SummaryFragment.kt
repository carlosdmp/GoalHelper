package apps.cdmp.goalhelper.presentation.ui.summary


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import apps.cdmp.goalHelper.databinding.SummaryFragmentBinding
import apps.cdmp.goalhelper.presentation.ui.main.MainButtonLogo
import apps.cdmp.goalhelper.presentation.ui.main.MainHosted
import apps.cdmp.goalhelper.presentation.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import apps.cdmp.goalhelper.presentation.ui.summary.adapter.SummaryAdapter


class SummaryFragment : Fragment(), MainHosted {

    override fun onFabClick() {
        binding.summary.findNavController()
            .navigate(SummaryFragmentDirections.actionSummaryFragmentToAddgoalFragment())
    }

    override fun onNavigationLanded() {
        summaryViewModel.loadGoals()
    }

    private lateinit var binding: SummaryFragmentBinding
    private val summaryViewModel: SummaryViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SummaryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.showFab(MainButtonLogo.DONE)
//        summaryViewModel.summaryGoalsUI.observe(this, Observer { goals ->
//            binding.summaryRecyclerView.withModels {
//                goals.undoneItemUIS.forEach { summaryItem ->
//                    summaryItemHolder {
//                        id(summaryItem.id)
//                        name(summaryItem.name)
//                        deadline(summaryItem.deadline)
//                        isDone(summaryItem.isDone)
//                        onDoneClick { _, _, _, _ ->
//                            summaryItem.onClickDone()
//                        }
//                    }
//                }
//                goals.doneItemUIS.forEach { summaryItem ->
//                    summaryItemHolder {
//                        id(summaryItem.id)
//                        name(summaryItem.name)
//                        deadline(summaryItem.deadline)
//                        isDone(summaryItem.isDone)
//                        onDoneClick { _, _, _, _ ->
//                            summaryItem.onClickDone()
//                        }
//                    }
//                }
//            }
//        })
        summaryViewModel.loading.observe(this, Observer { isLoading ->
            binding.loading = isLoading
        })
        summaryViewModel.loadGoals()
    }

    private fun initRecyclerView(){
     //   binding.summaryRecyclerView.setHasFixedSize(true)
        val adapter = SummaryAdapter()
      // binding.summaryRecyclerView = LinearLayoutManager(context)
      //  recyclerView.itemAnimator = DefaultItemAnimator()
    }
    override fun onResume() {
        super.onResume()

    }
}
