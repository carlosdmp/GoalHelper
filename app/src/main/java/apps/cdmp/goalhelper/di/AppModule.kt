package apps.cdmp.goalhelper.di

import apps.cdmp.goalhelper.bindmodel.main.MainHost
import apps.cdmp.goalhelper.data.db.AppDatabase
import apps.cdmp.goalhelper.data.repository.GoalsRepo
import apps.cdmp.goalhelper.ui.addgoal.AddGoalViewModel
import apps.cdmp.goalhelper.ui.main.MainViewModel
import apps.cdmp.goalhelper.ui.summary.SummaryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    //single database
    single { AppDatabase.Builder.buildDatabase(androidContext()) }
    single { get<AppDatabase>().objectiveDao() }
    // single repo
    single { GoalsRepo(goalDao = get()) }

    viewModel { MainViewModel() }
    viewModel { SummaryViewModel(get()) }
    viewModel { AddGoalViewModel(get()) }
}