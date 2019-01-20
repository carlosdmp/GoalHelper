package apps.cdmp.goalhelper.presentation.ui.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.common.*
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.data.repository.GoalsRepo
import apps.cdmp.goalhelper.presentation.ui.default
import apps.cdmp.goalhelper.presentation.ui.summary.bindmodel.SummaryItem
import apps.cdmp.goalhelper.presentation.ui.summary.bindmodel.SummaryList
import kotlinx.coroutines.*
import java.util.*

class SummaryViewModel(private val goalsRepo: GoalsRepo) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val todayDate = Calendar.getInstance().time

    private val goals: MutableLiveData<Resource<List<Goal>>> = MutableLiveData<Resource<List<Goal>>>()
        .default(stillLoading())

    val summaryGoals: LiveData<SummaryList> =
        Transformations.map(goals) { dbGoals ->
            when (dbGoals) {
                is Success ->
                    SummaryList(
                        undoneHeader = "Undone",
                        undoneItems = dbGoals.data.filter { !it.isDone }.map {
                            SummaryItem(
                                id = it.id,
                                name = it.description,
                                deadline = todayDate remainingTo it.deadline,
                                isDone = it.isDone,
                                onClickDone = { updateGoal(it.id, !it.isDone) }
                            )
                        },
                        doneHeader = "Done",
                        doneItems = dbGoals.data.filter { it.isDone }.map {
                            SummaryItem(
                                id = it.id,
                                name = it.description,
                                deadline = todayDate remainingTo it.deadline,
                                isDone = it.isDone,
                                onClickDone = { updateGoal(it.id, !it.isDone) }
                            )
                        })
                else -> SummaryList("", emptyList(), "", emptyList())
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
        print("called")
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
