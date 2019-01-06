package apps.cdmp.goalhelper.data.repository

import apps.cdmp.goalhelper.data.db.GoalDao

/**
 * Repository module for handling data operations.
 */
class GoalsRepo(private val goalDao: GoalDao) {

    fun getObjectives() = goalDao.getGoals()

}
