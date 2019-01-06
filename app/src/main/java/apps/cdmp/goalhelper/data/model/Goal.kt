package apps.cdmp.goalhelper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey
    @ColumnInfo(name = "id") val plantId: String,
    val description: String,
    val isDone: Boolean
)