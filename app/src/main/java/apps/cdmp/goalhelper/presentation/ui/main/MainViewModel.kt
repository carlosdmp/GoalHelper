package apps.cdmp.goalhelper.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val mainHost = MutableLiveData<MainHost>()
    fun showFab(logo: MainButtonLogo) {
        mainHost.value = MainHost(logo)
    }

    fun hideFab() {
        mainHost.value = MainHost()
    }
}
