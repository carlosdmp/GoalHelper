package apps.cdmp.goalhelper.ui.summary


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import apps.cdmp.goalHelper.databinding.SummaryFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SummaryFragment : Fragment() {
    lateinit var binding: SummaryFragmentBinding
    val summaryViewModel: SummaryViewModel by viewModel()

    companion object {
        fun newInstance() = SummaryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SummaryFragmentBinding.inflate(inflater, container, false)
        binding.fabAdd.setOnClickListener { view ->
            view.findNavController().navigate(SummaryFragmentDirections.actionSummaryFragmentToAddgoalFragment())
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        summaryViewModel.goals.observe(this, Observer { goalListResource ->
            goalListResource.fold({ goalList ->
                binding.message.text = when {
                    goalList.isEmpty() -> "No goals"
                    else -> goalList.joinToString { it.description }
                }
                binding.message
            }, {error ->
                println(error)
                binding.message.text = error.throwable.message ?: "no msg"
            }, {
                println("loading")
                binding.message.text = "loading"
            })
        })
    }

}
