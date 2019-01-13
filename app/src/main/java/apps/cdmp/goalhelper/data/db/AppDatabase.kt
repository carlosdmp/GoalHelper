package apps.cdmp.goalhelper.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import apps.cdmp.goalhelper.data.model.Goal

/**
 * The Room database for this app
 */

const val DATABASE_NAME = "objetivehelper-db"


@Database(entities = [Goal::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun objectiveDao(): GoalDao

    object Builder {
        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
