package apps.cdmp.goalhelper.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apps.cdmp.goalhelper.bindmodel.main.MainButtonLogo
import apps.cdmp.goalhelper.bindmodel.main.MainHost

class MainViewModel : ViewModel() {

    val mainHost = MutableLiveData<MainHost>()
    var onClick = {}
    fun showFab(logo: MainButtonLogo, onClick: () -> Unit) {
        mainHost.value = MainHost(logo, onClick)
    }

    fun hideFab() {
        mainHost.value = MainHost()
    }

    fun onFabClicked() {
        onClick()
    }
}
