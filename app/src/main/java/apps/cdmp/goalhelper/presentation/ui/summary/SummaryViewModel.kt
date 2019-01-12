package apps.cdmp.goalhelper.presentation.ui.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.common.Loading
import apps.cdmp.goalhelper.common.Resource
import apps.cdmp.goalhelper.common.Success
import apps.cdmp.goalhelper.common.successWith
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.data.repository.GoalsRepo
import apps.cdmp.goalhelper.presentation.ui.summary.bindmodel.SummaryItem

class SummaryViewModel(goalsRepo: GoalsRepo) : ViewModel() {

    private val goals: LiveData<Resource<List<Goal>>> =
        Transformations.map(goalsRepo.getGoals()) { dbGoals ->
            successWith(dbGoals)
        }

    val summaryGoals: LiveData<List<SummaryItem>> =
        Transformations.map(goals) { dbGoals ->
            when (dbGoals) {
                is Success -> dbGoals.data.map { SummaryItem(it.description, "TODO") }
                else -> emptyList()
            }
        }

    val loading: LiveData<Boolean> =
        Transformations.map(goals) { dbGoals ->
            when (dbGoals) {
                is Loading -> true
                else -> false
            }
        }
}
