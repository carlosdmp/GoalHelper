package apps.cdmp.goalhelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.cdmp.goalhelper.bindmodel.addgoal.AddGoal

@Entity(tableName = "goals")
data class Goal(
    val description: String,
    val isDone: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    object Creator {
        fun create(addGoal: AddGoal) = Goal(addGoal.name.value, false)
    }
}