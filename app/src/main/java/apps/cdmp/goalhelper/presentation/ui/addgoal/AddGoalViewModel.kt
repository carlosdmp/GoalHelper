package apps.cdmp.goalhelper.ui.addgoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.bindmodel.addgoal.AddGoalForm
import apps.cdmp.goalhelper.bindmodel.addgoal.AddGoalValidation
import apps.cdmp.goalhelper.common.default
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.data.repository.GoalsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class AddGoalViewModel(private val goalsRepo: GoalsRepo) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var goalForm = AddGoalForm(name = "", deadline = null)

    val liveForm: MutableLiveData<AddGoalForm> =
        MutableLiveData<AddGoalForm>().default(goalForm)
    val formValidation: LiveData<AddGoalValidation> =
        Transformations.map(liveForm) { i -> AddGoalValidation(i.name.isEmpty()) }

    private fun modifyGoal(f: (AddGoalForm) -> AddGoalForm) {
        goalForm = goalForm.let(f)
        liveForm.value = goalForm
    }

    fun updateName(newName: String) {
        modifyGoal {
            it.copy(name = newName, nameError = null, done = false)
        }
    }

    private fun updateNameError(newNameError: String) {
        modifyGoal {
            it.copy(nameError = newNameError, done = false)
        }
    }

    fun updateDate(newDate: Date) {
        modifyGoal {
            it.copy(deadline = newDate, done = false)
        }
    }

    fun addGoal() {
        if (formValidation.value?.isOk == true) {
            uiScope.launch(Dispatchers.IO) {
                goalsRepo.addGoal(Goal.Creator.create(goalForm))
            }
            modifyGoal { it.copy(done = true)}
        } else {
            updateNameError("You must set a name")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
