package apps.cdmp.goalhelper.bindmodel.addgoal

import apps.cdmp.goalhelper.bindmodel.FormField
import java.util.*

data class AddGoal(var name: FormField<String>, var validated: Boolean, var deadline : Date?, var isDeadline: Boolean = true)
data class AddGoalError()
enum class FrequencyMeasure(val display: String) {
    HOURS("h"),
    MINUTES("min"),
    TIMES("times")
}