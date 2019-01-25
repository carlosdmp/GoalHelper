package apps.cdmp.goalhelper.di

import apps.cdmp.goalhelper.data.db.AppDatabase
import apps.cdmp.goalhelper.data.repository.GoalsRepo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val dbModule = module {

    single { AppDatabase.Builder.buildDatabase(androidContext()) }
    single { get<AppDatabase>().objectiveDao() }
}

val repoModule = module {
    single { GoalsRepo(goalDao = get()) }
}