package apps.cdmp.goalhelper.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import apps.cdmp.goalhelper.data.model.Goal
import org.koin.core.KoinContext
import org.koin.standalone.KoinComponent
import java.util.*
import java.util.concurrent.Executors

const val DATABASE_NAME = "objetivehelper-db"

@Database(entities = [Goal::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun objectiveDao(): GoalDao

    object Builder {
        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadScheduledExecutor().execute {
                                prepopulateData.forEach {
                                    db.insert("goals", SQLiteDatabase.CONFLICT_REPLACE, it)
                                }
                            }
                        }
                    })
                    .build()
        }

        private val prepopulateData: List<ContentValues>
            get() {
                val install = ContentValues()
                install.put("description", "Install a TODO app")
                install.put("isDone", true)
                install.put("deadline", Date().time)

                val test = ContentValues()
                test.put("description", "Test this awesome app")
                test.put("isDone", false)
                test.put("deadline", Date().time)

                return listOf(install, test)
            }
    }
}
