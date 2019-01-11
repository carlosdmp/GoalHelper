package apps.cdmp.goalhelper.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.bindmodel.main.MainButtonLogo
import apps.cdmp.goalhelper.bindmodel.main.MainHost

class MainViewModel : ViewModel() {
    val mainHost = MutableLiveData<MainHost>()
    fun showFab(logo: MainButtonLogo) {
        mainHost.value = MainHost(logo)
    }

    fun hideFab() {
        mainHost.value = MainHost()
    }
}
