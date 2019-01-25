package apps.cdmp.goalhelper.presentation.ui.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.common.Loading
import apps.cdmp.goalhelper.common.Resource
import apps.cdmp.goalhelper.common.stillLoading
import apps.cdmp.goalhelper.common.successWith
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.data.repository.GoalsRepo
import apps.cdmp.goalhelper.presentation.ui.default
import apps.cdmp.goalhelper.presentation.ui.summary.mapper.UiMapper
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryUI
import kotlinx.coroutines.*

class SummaryViewModel(private val goalsRepo: GoalsRepo, uiMapper: UiMapper) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val goals: MutableLiveData<Resource<List<Goal>>> = MutableLiveData<Resource<List<Goal>>>()
            .default(stillLoading())

    val summaryGoalsUI: LiveData<List<SummaryUI>?> =
            Transformations.map(goals) { dbGoals ->
                with(uiMapper) {
                    dbGoals.toUI(goalClicked = { id, done -> updateGoal(id, !done) })
                }
            }

    val loading: LiveData<Boolean> =
            Transformations.map(goals) { dbGoals ->
                when (dbGoals) {
                    is Loading -> true
                    else -> false
                }
            }

    private fun updateGoal(id: Int, done: Boolean) {
        uiScope.launch(Dispatchers.IO) {
            goalsRepo.updateGoal(id, done)
            delay(350)
            loadGoals()
        }
    }

    fun loadGoals() {
        uiScope.launch(Dispatchers.IO) {
            goals.postValue(stillLoading())
            val dbGoals = goalsRepo.getGoals()
            goals.postValue(successWith(dbGoals))
        }
    }
}
