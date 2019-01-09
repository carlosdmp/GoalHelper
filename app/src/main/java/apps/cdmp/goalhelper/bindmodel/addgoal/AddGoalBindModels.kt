package apps.cdmp.goalhelper.bindmodel.addgoal

import java.util.*

data class AddGoal(
    var name: String,
    var deadline: Date?,
    var isDeadline: Boolean = true
) {
    fun getFormErrors() = AddGoalError(
        nameError = name.isEmpty()
    )
}

data class AddGoalError(var nameError: Boolean = false) {
    val isOk
        get() = !nameError
}

enum class FrequencyMeasure(val display: String) {
    HOURS("h"),
    MINUTES("min"),
    TIMES("times")
}