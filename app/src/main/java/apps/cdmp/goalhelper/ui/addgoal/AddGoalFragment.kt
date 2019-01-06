package apps.cdmp.goalhelper.ui.addgoal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.cdmp.goalHelper.R
import org.koin.android.viewmodel.ext.android.viewModel

class AddGoalFragment : Fragment() {

    companion object {
        fun newInstance() = AddGoalFragment()
    }

    private val viewModel: AddGoalViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_goal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun showDoneFab(){

    }

}
