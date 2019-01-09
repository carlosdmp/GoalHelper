package apps.cdmp.goalhelper.bindmodel.main

data class MainHost(val logo: MainButtonLogo = MainButtonLogo.HIDDEN, val onClick: () -> Unit = {})

enum class MainButtonLogo { HIDDEN, ADD, DONE }