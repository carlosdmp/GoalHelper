package apps.cdmp.goalhelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.cdmp.goalhelper.presentation.ui.addgoal.bindmodel.AddGoalForm
import java.util.*

@Entity(tableName = "goals")
data class Goal(
    val description: String,
    val deadline : Date,
    var isDone: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    object Creator {
        fun create(addGoalForm: AddGoalForm) = Goal(addGoalForm.name, addGoalForm.deadline ?: Date(), false)
    }
}