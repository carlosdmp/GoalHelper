package apps.cdmp.goalhelper.bindmodel.addgoal

import java.util.*

data class AddGoalForm(
    val name: String,
    val nameError: String? = null,
    val deadline: Date?,
    val isDeadline: Boolean = true,
    val done: Boolean = false
) {
    fun getValidation() = AddGoalValidation(
        nameError = name.isEmpty()
    )
}

data class AddGoalValidation(var nameError: Boolean = false) {
    val isOk
        get() = !nameError
}

enum class FrequencyMeasure(val display: String) {
    HOURS("h"),
    MINUTES("min"),
    TIMES("times")
}