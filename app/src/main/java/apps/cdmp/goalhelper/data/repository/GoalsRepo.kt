package apps.cdmp.goalhelper.data.repository

import androidx.annotation.WorkerThread
import apps.cdmp.goalhelper.data.db.GoalDao
import apps.cdmp.goalhelper.data.model.Goal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Repository module for handling data operations.
 */
class GoalsRepo(private val goalDao: GoalDao) {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun getGoals() = goalDao.getGoals()

    @WorkerThread
    suspend fun addGoal(goal: Goal) {
        goalDao.insert(goal)
    }

}
