package apps.cdmp.goalhelper.di

import apps.cdmp.goalhelper.presentation.ui.addgoal.AddGoalViewModel
import apps.cdmp.goalhelper.presentation.ui.main.MainViewModel
import apps.cdmp.goalhelper.presentation.ui.summary.SummaryViewModel
import apps.cdmp.goalhelper.presentation.ui.summary.adapter.SummaryAdapter
import apps.cdmp.goalhelper.presentation.ui.summary.mapper.UiMapper
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mainModule = module {
    viewModel { MainViewModel() }
}
val summaryModule = module {
    viewModel { SummaryViewModel(goalsRepo = get(), uiMapper = get()) }
    single { UiMapper(resources = androidContext().resources) }
    factory { SummaryAdapter(uiMapper = get()) }
}

val addGoalModule = module {
    viewModel { AddGoalViewModel(goalsRepo = get()) }
}