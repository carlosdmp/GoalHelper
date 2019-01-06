package apps.cdmp.goalhelper.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import apps.cdmp.goalhelper.data.model.Goal

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY id")
    fun getGoals(): LiveData<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: List<Goal>)
}
