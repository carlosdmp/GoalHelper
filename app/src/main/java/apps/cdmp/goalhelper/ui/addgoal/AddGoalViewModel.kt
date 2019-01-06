package apps.cdmp.goalhelper.ui.addgoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.data.repository.GoalsRepo

class AddGoalViewModel(private val goalsRepo: GoalsRepo) : ViewModel() {
    val newGoal : LiveData<Goal> = MutableLiveData()

}
