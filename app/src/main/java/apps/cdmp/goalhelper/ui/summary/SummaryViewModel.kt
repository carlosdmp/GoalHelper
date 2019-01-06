package apps.cdmp.goalhelper.ui.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.common.Resource
import apps.cdmp.goalhelper.common.successWith
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.data.repository.GoalsRepo

class SummaryViewModel(private val goalsRepo: GoalsRepo) : ViewModel() {

    val goals: LiveData<Resource<List<Goal>>> =
        Transformations.map(goalsRepo.getObjectives()) { dbGoals ->
            successWith(dbGoals)
        }
}
