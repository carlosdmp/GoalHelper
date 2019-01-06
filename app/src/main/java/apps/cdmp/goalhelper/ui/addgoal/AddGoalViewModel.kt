package apps.cdmp.goalhelper.ui.addgoal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.bindmodel.FormField
import apps.cdmp.goalhelper.bindmodel.addgoal.AddGoal
import apps.cdmp.goalhelper.common.default
import apps.cdmp.goalhelper.data.repository.GoalsRepo

class AddGoalViewModel(private val goalsRepo: GoalsRepo) : ViewModel() {
    val newGoal: MutableLiveData<AddGoal> =
        MutableLiveData<AddGoal>().default(AddGoal(name = FormField(""), validated = false))

    fun modifyGoal(f: AddGoal.() -> Unit) {
        newGoal.value?.let(f)
        newGoal.postValue(newGoal.value)
    }

    fun updateName(newName: String) {
        modifyGoal {
            name.value = newName
            if (newName.isEmpty()) {
                name.error = "Select a name"
            } else {
                name.error = null
            }
        }
    }


}
