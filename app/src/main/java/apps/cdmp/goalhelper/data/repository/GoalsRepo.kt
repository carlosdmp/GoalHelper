package apps.cdmp.goalhelper.data.repository

import apps.cdmp.goalhelper.data.db.GoalDao
import apps.cdmp.goalhelper.data.model.Goal

/**
 * Repository module for handling data operations.
 */
class GoalsRepo(private val goalDao: GoalDao) {

    fun getGoals() = goalDao.getGoals()

    fun addGoal(goal: Goal) {
        goalDao.insert(goal)
    }

}
