package apps.cdmp.goalhelper

import android.app.Application
import apps.cdmp.goalhelper.di.appModules
import org.koin.android.ext.android.startKoin

class GoalHelperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModules)
    }
}