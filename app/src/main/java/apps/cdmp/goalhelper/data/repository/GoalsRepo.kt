package apps.cdmp.goalhelper.data.repository

import androidx.annotation.WorkerThread
import apps.cdmp.goalhelper.data.db.GoalDao
import apps.cdmp.goalhelper.data.model.Goal

class GoalsRepo(private val goalDao: GoalDao) {

    fun getGoals() = goalDao.getGoals()

    @WorkerThread
    suspend fun addGoal(goal: Goal) {
        goalDao.insert(goal)
    }

    @WorkerThread
    suspend fun updateGoal(id: Int, isDone: Boolean) {
        goalDao.update(id, isDone)
    }

}
