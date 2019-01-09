package apps.cdmp.goalhelper.ui.addgoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.bindmodel.addgoal.AddGoal
import apps.cdmp.goalhelper.bindmodel.addgoal.AddGoalError
import apps.cdmp.goalhelper.common.default
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.data.repository.GoalsRepo
import java.util.*

class AddGoalViewModel(private val goalsRepo: GoalsRepo) : ViewModel() {

    val newGoal: MutableLiveData<AddGoal> = MutableLiveData<AddGoal>().default(AddGoal(name = "", deadline = null))
    val errors: LiveData<AddGoalError> = Transformations.map(newGoal) { i -> AddGoalError(i.name.isEmpty()) }

    private fun modifyGoal(f: AddGoal.() -> Unit) {
        newGoal.value?.let(f)
        newGoal.postValue(newGoal.value)
    }


    fun updateName(newName: String) {
        modifyGoal {
            name = newName
        }
    }

    fun setDate(date: Date) {
        modifyGoal {
            deadline = date
        }
    }

    fun addGoal(addGoal: AddGoal) {
        goalsRepo.addGoal(Goal.Creator.create(addGoal))
    }
}
